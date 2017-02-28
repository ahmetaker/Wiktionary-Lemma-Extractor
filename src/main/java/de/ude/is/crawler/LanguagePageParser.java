package de.ude.is.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Class {@code LanguagePageParser} parses the language page
 * <p> Extract the language properties (name, code, ancestors and scripts)
 * see <a href="https://en.wiktionary.org/wiki/Category:English_language">English language</a> page on Wiktionary
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 1/24/2017
 */
public class LanguagePageParser {
    private static final String WIKTIONARY_URL = "https://en.wiktionary.org/w/index.php" +
            "?title=%s" +
            "&printable=yes";

    /**
     * Extract language properties
     *
     * @param languageInfoTable
     * @return
     */
    private LangInfo extractLanguageInfo(Element languageInfoTable) {
        LangInfo langInfo = new LangInfo();
        for (Element tr : languageInfoTable.select("tr")) {
            String prop = tr.select("th").text();
            String value = tr.select("td").text();
            switch (prop) {
                case "Canonical name":
                    langInfo.setCanonicalName(value);
                    break;
                case "Language code":
                    langInfo.setLanguageCode(value);
                    break;
                case "Language family":
                    langInfo.setLanguageFamily(value);
                    break;
                case "Ancestors":
                    langInfo.setAncestors(value);
                    break;
                case "Scripts":
                    langInfo.setScripts(value);
                    break;
            }
        }
        return langInfo;
    }


    private Element getLanguageInfoTable(Document doc) {
        for (Element table : doc.select(Selectors.WIKI_TABLE.toString())) {
            Elements links = table.select("a");
            if (links.size() > 0) {
                if (links.first().text().equals("Edit language data")) {
                    return table;
                }
            }
        }
        return null;
    }

    /**
     * Extract the html version of the language page
     *
     * @param languageCategoryTitle language page title
     * @return {@link LangInfo}
     * @throws IOException
     */
    public LangInfo getLanguageInfo(String languageCategoryTitle) throws IOException {
        try {
            Document doc = Jsoup.connect(String.format(WIKTIONARY_URL, languageCategoryTitle)).get();
            Element languageInfoTable = getLanguageInfoTable(doc);
            if (languageInfoTable != null) {
                LangInfo langInfo = extractLanguageInfo(languageInfoTable);
                return langInfo;
            }
        } catch (SocketTimeoutException ex) {
            return getLanguageInfo(languageCategoryTitle);
        } catch (Exception ex) {
            return null;
        }
        return null;
    }
}
