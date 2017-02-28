package de.ude.is.crawler;

import de.ude.is.parselinksmodel.Link;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class {@code SectionParser} parses a section of a page
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 2/3/2017.
 */
public class SectionParser {

    /**
     * Witionary pages of common types of word forms. these pages will be excluded from being processed as part of the page links.
     */
    private static final Set<String> EXCLUDED_LINKS = new HashSet<String>() {{
        add("/wiki/strong_declension");
        add("/wiki/masculine");
        add("/wiki/singular");
        add("/wiki/genitive");
        add("/wiki/dative");
        add("/wiki/weak_declension");
        add("/wiki/nominative");
        add("/wiki/plural");
        add("/wiki/feminine");
    }};

    /**
     * Section object
     */
    private final Document content;

    private final String pageTitle;
    private final String sectionTitle;

    /**
     * Section's language
     */
    private String sectionLanguage;

    /**
     * All links of the entire page
     */
    private Map<String, Link> pageLinks;

    /**
     * Indicates whether this section is a lemma form or not
     */
    private boolean lemma;


    public SectionParser(String pageTitle, String sectionTitle, Document content,
                         Map<String, Link> pageLinks, String sectionLanguage, Document translation) {
        this.sectionLanguage = sectionLanguage;
        this.content = content;
        this.sectionTitle = sectionTitle;
        this.pageTitle = pageTitle;
        this.pageLinks = pageLinks;
    }


    /**
     * Parsing process
     *
     * @return {@link Section}
     * @throws IOException
     */
    public Section parseSection() throws IOException {
        Section section = new Section();
        section.setTitle(this.sectionTitle);
        section.setLanguage(this.sectionLanguage);
        section.setWordsList(getWordForms());
        // order of setLemma is important, because lemma determined during getWordForms
        section.setLemma(this.lemma);
        return section;
    }

    /**
     * Gets the word forms of a section
     *
     * @return {@link Set<String>} collected word forms
     * @throws IOException
     */
    private Set<String> getWordForms() throws IOException {
        content.select(Selectors.TOC.toString()).remove();
        content.select(Selectors.EDIT_SECTION.toString()).remove();
        content.select(Selectors.SISTER_PROJECT.toString()).remove();

        Set<Link> sectionLinks = new HashSet<Link>();
        Set<String> forms = new HashSet<String>();
        Elements searchDomain = null;

        Element firstHeadline = content.select(Selectors.HEADLINE_CLASS.toString()).first();
        searchDomain = getNextPTag(firstHeadline);
        boolean lemmaSection = searchDomain.text().contains("(");
        this.lemma = lemmaSection;
        if (!lemmaSection) {
            searchDomain = content.select("li");
        }

        if (sectionTitle.toUpperCase().equals(POS.VERB.toString())) removeAuxiliary(searchDomain);

        Elements anchors = searchDomain.select("a"); // select only clickable elements
        // TODO: import lines from anchor url(with url decoder). see this page for more info: https://en.wiktionary.org/wiki/%D8%A3%D8%A8%D8%A7%D9%84%D8%BA#Arabic
        for (Element anchor : anchors) {
            Link link = pageLinks.get(anchor.text());
            if (link != null && link.getNs() == 0) {
                if (!EXCLUDED_LINKS.contains(anchor.attr("href"))) {
                    sectionLinks.add(link);
                }
            }
        }

        // include conjunstions for verbs and adjecents for nouns, adjectives
        // ready to process seealso, related words ..etc

        if (lemmaSection || (!lemmaSection && sectionLinks.size() == 1)) {
            forms = sectionLinks.stream().map(Link::getValue).collect(Collectors.toSet());
        }

        if (!lemmaSection && sectionLinks.size() > 1) {
            for (Link link : sectionLinks) {
                if (checkWordExistenceByLanguageAndPos(link.getValue())) {
                    forms.add(link.getValue());
                }
            }
        }
//        if (!lemmaSection && forms.size() > 1) {
//            //there should be a problem.
//            System.err.println("page " + this.pageTitle + " is not a lemma page and has more than one link");
//        }

        if (lemmaSection) {
            forms.add(pageTitle);
        }
        return forms;
    }

    private Elements getNextPTag(Element firstHeadline) {
        Element nextElement = firstHeadline.nextElementSibling();
        if (lemma && !nextElement.tagName().equals("p")) {
            System.out.println(this.pageTitle + " is lemma and does not has a p tag after section title");
        }

        while (nextElement != null) {
            if (nextElement.tagName().equals("p")) return new Elements(nextElement);
            else nextElement = nextElement.nextElementSibling();
        }
        return null;
    }


    /**
     * Check if a word is existed in a language using part of speech
     *
     * @return {@code true} if word with its part of speech is existed in a language, {@code false} otherwise
     * @throws IOException
     */
    private boolean checkWordExistenceByLanguageAndPos(String title) throws IOException {
        // TODO: change signature, improve methodology, javadoc
        PageSections pageSections = new PageSections(title);
        Set<String> languages = pageSections.getLanguages();
        if (languages.contains(this.sectionLanguage)) {
            if (pageSections.getLanguageSections(this.sectionLanguage).contains(this.sectionTitle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes auxiliary from a given elements
     *
     * @param elements Given HTML elements
     */
    private void removeAuxiliary(List<Element> elements) {
        List<Integer> elementsToRemove = new ArrayList<>();
        boolean removeAfterAuxiliary = false;
        int i = 0;
        while (i < elements.get(0).children().size()) {
            if (elements.get(0).children().get(i).text().trim().startsWith("auxiliary")) {
                removeAfterAuxiliary = true;
            }
            if (removeAfterAuxiliary) {
                elements.get(0).children().get(i).remove();
                continue;
            }
            i++;
        }
    }
}
