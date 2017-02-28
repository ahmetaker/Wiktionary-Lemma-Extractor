package de.ude.is.crawler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.ude.is.categorymembersmodel.Categorymember;
import de.ude.is.categorymembersmodel.CategoryMembersAPI;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Class {@code LanguageWordsCollector} Collects all words of a language.
 * <p> see <a href="https://en.wiktionary.org/w/api.php?action=query&list=categorymembers&cmtitle=Category:English_lemmas&cmlimit=500&format=xml&cmtype=page">API example</a> for English lemmas
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 1/27/2017
 */
public class LanguageWordsCollector implements Iterable<String> {
    /**
     * Use query API of Wiktionary to collect all words in one category of a language.
     */
    private static final String CATEGORY_PAGE_API = "https://en.wiktionary.org/w/api.php" +
            "?action=query" +
            "&list=categorymembers" +
            "&cmtitle=Category:%s_%s" +
            "&cmlimit=500" +
            "&format=json" +
            "&cmcontinue=%s" +
            "&cmtype=page";
    private static final Logger LOGGER = Logger.getLogger(LanguageWordsCollector.class.getName());
    private final LangInfo language;
    private int listSize = 0;
    private List<String> wordsList;

    public LanguageWordsCollector(LangInfo language) throws IOException {
        this.language = language;
        setup();
    }

    /**
     * Setup the collect process, include words of lemma and non-lemma pages
     *
     * @throws IOException
     */
    private void setup() throws IOException {
        this.wordsList = new LinkedList<>();
        extractCategoryWords(true);
        extractCategoryWords(false);
    }

    /**
     * Browse the Category page recursively and collect all words in both lemma and non-lemma pages
     *
     * @param lemmas       indicates the targeted page "lemmas" or "non-lemma_forms"
     * @param continueCode Optional ConinueCode returned by query API
     * @throws IOException
     */
    private void extractCategoryWords(boolean lemmas, String... continueCode) throws IOException {
        String cmcontinue = continueCode.length > 0 ? continueCode[0] : "";
        String lemmasOrNonLemmas = lemmas ? "lemmas" : "non-lemma_forms";
        String url = String.format(CATEGORY_PAGE_API, URLEncoder.encode(this.language.getCanonicalName(), "UTF-8"),
                lemmasOrNonLemmas, cmcontinue);
        String json = null;
        try {
            json = new URLReader(url).read();
        } catch (Exception ex) {
            LOGGER.log(Level.ERROR, "unable to read: " + url, ex);

        }

        Gson gson = new GsonBuilder().create();
        CategoryMembersAPI categoryMembersAPI = gson.fromJson(json, CategoryMembersAPI.class);

        List<Categorymember> pages = categoryMembersAPI.getQuery().getCategorymembers();
        for (Categorymember page : pages) {
            this.wordsList.add(page.getTitle());
        }
        listSize += pages.size();
        if (categoryMembersAPI.getContinue() != null) {
            extractCategoryWords(lemmas, categoryMembersAPI.getContinue().getCmcontinue());
        }
    }

    @Override
    public Iterator<String> iterator() {
        return wordsList.iterator();
    }

    public int getListSize() {
        return listSize;
    }

    public LangInfo getLanguage() {
        return language;
    }
}
