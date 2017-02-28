package de.ude.is.crawler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.ude.is.categorymembersmodel.CategoryMembersAPI;
import de.ude.is.categorymembersmodel.Categorymember;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class {@code WiktionaryLanguagesCrawler} reads the HTML content of a url
 * <p> see <a href="https://en.wiktionary.org/w/api.php?action=query&list=categorymembers&cmtitle=Category:All_languages&cmlimit=500&format=xml">Wiktionry languages</a>
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 1/24/2017.
 */
public class WiktionaryLanguagesCrawler {
    /**
     * API for reading all Wiktionary languages
     */
    private static final String ALL_LANGUAGES_URL = "https://en.wiktionary.org/w/api.php" +
            "?action=query" +
            "&list=categorymembers" +
            "&cmtitle=Category:All_languages" +
            "&cmlimit=500" +
            "&format=json" +
            "&cmcontinue=%s";
    private static final Logger LOGGER = Logger.getLogger(WiktionaryLanguagesCrawler.class.getName());
    /**
     * Singleton instance to prevent creating more then one language crawler
     */
    private static WiktionaryLanguagesCrawler languagesCrawler = null;
    private static int count = 0;
    /**
     * Save all languages in a {@link Map<String, LangInfo}
     */
    private Map<String, LangInfo> allLangs = new HashMap<>();

    private WiktionaryLanguagesCrawler() {
        try {
            setupWiktionaryLanguages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the singelton instance of languages crawler
     *
     * @return {@link WiktionaryLanguagesCrawler}
     */
    public static WiktionaryLanguagesCrawler getInstance() {
        if (languagesCrawler == null) {
            languagesCrawler = new WiktionaryLanguagesCrawler();
        }
        return languagesCrawler;
    }


    /**
     * sets up the languages crawler
     */
    private void setupWiktionaryLanguages() throws IOException {
        File langInfoFile = new File("lang-info");
        if (langInfoFile.exists() && !langInfoFile.isDirectory()) {
            System.out.println("loading lang-info file");
            read();
        } else {
            System.out.println("lang-info file is not extracted yet. extracting process will start in moments");
            readAllLanguages();
            save(allLangs);
        }
    }

    /**
     * Getter for all languages
     *
     * @return {@link Map<String, LangInfo>}
     */
    public Map<String, LangInfo> getAllLanguages() {
        return this.allLangs;
    }

    /**
     * Reads all Wiktionary languages form a local file
     */
    private void read() {
        HashMap<String, LangInfo> map = null;
        try {
            FileInputStream fis = new FileInputStream("lang-info");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.allLangs = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
    }

    /**
     * Saves the collected languages into a local file
     *
     * @param allLangs collected languages list
     */
    private void save(Map<String, LangInfo> allLangs) {
        try {
            FileOutputStream fos =
                    new FileOutputStream("lang-info");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allLangs);
            oos.close();
            fos.close();
            System.out.printf("lang-info file generated and saved successfully");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Reads the languages form the Wiktionary API
     *
     * @param continueCode
     * @throws IOException
     */
    private void readAllLanguages(String... continueCode) throws IOException {
        String cmcontinue = continueCode.length > 0 ? continueCode[0] : "";
        String url = String.format(ALL_LANGUAGES_URL, cmcontinue);
        String json = null;
        try {
            json = new URLReader(url).read();
        } catch (Exception ex) {
            LOGGER.log(org.apache.log4j.Level.ERROR, "unable to read: " + url, ex);
        }
        Gson gson = new GsonBuilder().create();
        CategoryMembersAPI categoryMembersAPI = gson.fromJson(json, CategoryMembersAPI.class);

        List<Categorymember> categoryMembers = categoryMembersAPI.getQuery().getCategorymembers();
        for (Categorymember categoryMember : categoryMembers) {
            LangInfo langInfo = new LanguagePageParser().getLanguageInfo(categoryMember.getTitle());
            if (langInfo != null) {
                System.out.println(++count + ": " + langInfo.getCanonicalName());
                allLangs.put(langInfo.getCanonicalName().toLowerCase(), langInfo);
            }
        }

        if (categoryMembersAPI.getContinue() != null) {
            readAllLanguages(categoryMembersAPI.getContinue().getCmcontinue());
        }
    }

    /**
     * Search for a language
     *
     * @param language language name
     * @return {@link LangInfo}
     */
    public LangInfo search(String language) {
        return this.allLangs.get(language.toLowerCase());
    }
}
