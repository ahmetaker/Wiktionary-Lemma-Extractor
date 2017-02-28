package de.ude.is.crawler;

import com.google.gson.Gson;
import de.ude.is.dal.DBService;
import de.ude.is.parselinksmodel.Link;
import de.ude.is.parselinksmodel.ParseLinksAPI;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

interface CompleteParsingListener {
    void pageProcessed();
}

/**
 * Class {@code PageParser} parses a page
 * <p> see <a href="https://en.wiktionary.org/w/api.php?action=parse&prop=links&format=xml&page=test">API example</a> of test page links
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 2/3/2017.
 */
public class PageParser implements Runnable {

    /**
     * All links in a page API
     */
    private static final String PARSE_LINKS_API = "https://en.wiktionary.org/w/api.php" +
            "?action=parse" +
            "&prop=links" +
            "&page=%s" +
            "&format=json";
    private final static Logger LOGGER = Logger.getLogger(PageParser.class.getName());
    /**
     * page title
     */
    private final String title;
    private CompleteParsingListener onCompleteListener;
    private String inputLanguage;

    public PageParser(String title, String inputLanguage) {
        this.title = title;
        this.inputLanguage = inputLanguage;
    }

    @Override
    public void run() {
        try {
            List<Section> sections = parsePage();
            sections.forEach(this::save);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (this.onCompleteListener != null) this.onCompleteListener.pageProcessed();
        }
    }

    /**
     * Parse the page languages and sections
     *
     * @return {@link List<Section>} the final output of the parsed page
     * @throws IOException
     */
    public List<Section> parsePage() throws IOException {
        Map<String, Link> pageLinks = getPageLinks();
        PageFetcher pageFetcher = new PageFetcher(title);
        String html = pageFetcher.fetchPage();
        List<StringBuilder> allSections = getPageSections(html);
        PageSections pageSections = new PageSections(title);
        Set<String> languagesSections = pageSections.getLanguages();
        List<LanguageEntry> languageEntries = getLanguagesSections(languagesSections, allSections);
        List<Section> parsedSections = new ArrayList<>();
        for (LanguageEntry languageEntry : languageEntries) {
            if (languageEntry.getLanguage() != null && languageEntry.getLanguage().equals(this.inputLanguage)) {
                for (ParsingSection sectionDocument : languageEntry.getSections()) {
                    String sectionTitle = sectionDocument.getTitle();
                    if (sectionTitle.equalsIgnoreCase(POS.VERB.toString())
                            || sectionTitle.equalsIgnoreCase(POS.NOUN.toString())
                            || sectionTitle.equalsIgnoreCase(POS.ADJECTIVE.toString())
                            || sectionTitle.equalsIgnoreCase(POS.ADVERB.toString())) {
                        SectionParser sectionParser = new SectionParser(title,
                                sectionTitle,
                                sectionDocument.getDocument(),
                                pageLinks,
                                languageEntry.getLanguage(),
                                searchForTranslationSection(sectionDocument.getIndex(), languageEntry.getSections()));
                        Section processedSection = sectionParser.parseSection();
                        parsedSections.add(processedSection);
                    }
                }
            }
        }
        return parsedSections;
    }

    //TODO: search for translations of a section
    private Document searchForTranslationSection(int index, List<ParsingSection> sections) {
//        int searchIndex = index + 1;
//        while (!POS.TARGETED_POS.contains(sections.get(searchIndex).getTitle().toUpperCase())
//                && searchIndex < sections.size()) {
//            if (sections.get(searchIndex).getTitle().equalsIgnoreCase("Translations")
//                    || sections.get(searchIndex).getTitle().equalsIgnoreCase("Translation")) {
//                return sections.get(searchIndex).getDocument();
//            }
//            searchIndex++;
//        }
        return null;
    }

    /**
     * Gets the internal sections of a language
     *
     * @param languagesSections
     * @param allSections       {@link List<StringBuilder>} HTML list of the sections
     * @return {@link List<LanguageEntry>}
     */
    private List<LanguageEntry> getLanguagesSections(Set<String> languagesSections, List<StringBuilder> allSections) {
        List<LanguageEntry> languageEntries = new ArrayList<>();
        int sectionIndex = 0;
        for (StringBuilder section : allSections) {
            Document sectionDocument = Jsoup.parse(section.toString());
            String sectionTitle = sectionDocument.select(Selectors.HEADLINE_CLASS.toString()).text();
            if (languagesSections.contains(sectionTitle)) {
                languageEntries.add(new LanguageEntry(title, sectionTitle));
                sectionIndex = 0;
            } else {
                if (languageEntries.size() == 0) {
                    languageEntries.add(new LanguageEntry(title, null));
                    sectionIndex = 0;
                }
                languageEntries.get(languageEntries.size() - 1).addSection(new ParsingSection(sectionIndex, sectionTitle, sectionDocument));
                sectionIndex++;
            }

        }
        return languageEntries;
    }

    /**
     * Gets the sections of a page
     *
     * @param htmlText HTML of the page
     * @return {@link List<StringBuilder>} HTML list of the page sections
     */
    private List<StringBuilder> getPageSections(String htmlText) {
        String[] lines = htmlText.split(Selectors.NEWLINE.toString());
        int index = 0;
        List<StringBuilder> sectionsStrings = new ArrayList<>();
        while (index < lines.length) {
            if (lines[index].contains(Selectors.HEADLINE.toString())) {
                sectionsStrings.add(new StringBuilder());
                sectionsStrings.get(sectionsStrings.size() - 1).append(lines[index]);
            } else {
                if (sectionsStrings.size() == 0) sectionsStrings.add(new StringBuilder());
                sectionsStrings.get(sectionsStrings.size() - 1).append(lines[index]);
            }
            index++;
        }
        return sectionsStrings;
    }


    /**
     * Gets the page links
     * see {@link Link}
     *
     * @return {@Code Map<String, Link>} link text is the key.
     * @throws IOException
     */
    private Map<String, Link> getPageLinks() throws IOException {
        String json = null;
        String url = String.format(PARSE_LINKS_API, URLEncoder.encode(this.title, "UTF-8"));
        try {
            json = new URLReader(url).read();
        } catch (Exception ex) {
            LOGGER.log(Level.ERROR, "unable to read: " + url, ex);
        }
        Gson gson = new Gson();
        ParseLinksAPI parseLinksAPI = gson.fromJson(json, ParseLinksAPI.class);
        Map<String, Link> links =
                parseLinksAPI.getParse().getLinks().stream().collect(Collectors.toMap(Link::getValue,
                        Function.identity()));
        return links;
    }


    /**
     * Saves the section data into the database
     *
     * @param section
     */
    private void save(Section section) {
        Set<String> words = section.getWordsList();
        for (String word : words) {
            if (section.isLemma()) {
                DBService.saveWordsList(title, section.getLanguage(), section.getTitle(), word);
            } else {
                DBService.saveWordsList(word, section.getLanguage(), section.getTitle(), title);
            }
        }
    }

    public CompleteParsingListener getOnCompleteListener() {
        return onCompleteListener;
    }

    public void setOnCompleteListener(CompleteParsingListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }
}