package de.ude.is.crawler;

import com.google.gson.Gson;
import de.ude.is.parsesectionsmodel.*;
import de.ude.is.parsesectionsmodel.Section;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class {@code PageSections} processes the sections of a page
 * <p> see <a href="https://en.wiktionary.org/w/api.php?action=parse&prop=sections&format=xml&page=test">API example</a> of test page
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 1/27/2017.
 */
public class PageSections {
    /**
     * Wiktionary API to fetch the sections of a page
     */
    private static final String PARSE_SECTIONS_API = "https://en.wiktionary.org/w/api.php" +
            "?action=parse" +
            "&prop=sections" +
            "&format=json" +
            "&page=%s";

    /**
     * Headline level of language section as defined in Wiktionary
     */
    private static final String LANGUAGE_HEADLINE_LEVEL = "2";
    private static final Logger LOGGER = Logger.getLogger(PageSections.class.getName());
    /**
     * Wiktionary page title
     */
    private String pageTitle;
    /**
     * All sections of a page
     */
    private List<Section> allSections;

    public PageSections(String pageTitle) throws IOException {
        this.pageTitle = pageTitle;
    }

    /**
     * Gets a list of {@link Section}, each entry represents a language section
     *
     * @return {@link LanguageEntry}
     * @throws IOException
     */
    public Set<String> getLanguages() throws IOException {
        String json = null;
        String url = String.format(PARSE_SECTIONS_API, URLEncoder.encode(this.pageTitle, "UTF-8"));
        try {
            json = new URLReader(url).read();
        } catch (Exception ex) {
            LOGGER.log(Level.ERROR, "unable to read: " + url, ex);
        }
        Gson gson = new Gson();
        ParseSectionsAPI parseSectionsAPI = gson.fromJson(json, ParseSectionsAPI.class);
        List<de.ude.is.parsesectionsmodel.Section> sections = parseSectionsAPI.getParse().getSections();
        allSections = sections;
        Set<String> languages =
                sections.stream().filter(section -> section.getLevel().equals(LANGUAGE_HEADLINE_LEVEL))
                        .map(section -> section.getLine()).collect(Collectors.toSet());

        return languages;
    }

    /**
     * Gets sections of a language using language name
     *
     * @param language language name
     * @return {@link List<String>} titles of the sections
     */
    public List<String> getLanguageSections(String language) {
        Section languageSection = allSections.stream().filter(section -> section.getLine().equals(language)).findFirst().get();
        List<String> languageSections = allSections.stream()
                .filter(section -> section.getNumber().startsWith(languageSection.getNumber()))
                .map(section -> section.getLine()).collect(Collectors.toList());
        return languageSections;
    }

    public List<Section> getAllSections() {
        return allSections;
    }
}