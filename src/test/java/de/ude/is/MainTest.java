package de.ude.is;

import de.ude.is.crawler.PageParser;
import de.ude.is.crawler.Section;
import org.junit.Test;
import org.junit.Ignore;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * {@code MainTest} performs a parsing test on Wiktionary page "test"
 * <p> see <a href="https://en.wiktionary.org/wiki/test">Wiktionary test page</a>
 */
public class MainTest {

    String pageTitle = "test";
    String language = "English";
    PageParser parser = new PageParser(pageTitle, language);
    List<Section> expectedResultsOfTestPage = new ArrayList<Section>() {{
        boolean lemma;
        add(new Section(language, "Noun", (lemma = true),
                new HashSet<String>(Arrays.asList(new String[]{"test", "tests"}))));
        add(new Section(language, "Verb", (lemma = true),
                new HashSet<String>(Arrays.asList(new String[]{"test", "tests", "testing", "tested"}))));
        add(new Section(language, "Noun", (lemma = true),
                new HashSet<String>(Arrays.asList(new String[]{"test", "tests"}))));
        add(new Section(language, "Verb", (lemma = true),
                new HashSet<String>(Arrays.asList(new String[]{"test", "tests", "testing", "tested"}))));

    }};

    @Test
    public void testParsePage() throws IOException {
        List<Section> sections = parser.parsePage();
        List<Section> englishSections = sections.stream()
                .filter(section -> section.getLanguage().equals(language))
                .collect(Collectors.toList());
        assertTrue(expectedResultsOfTestPage.containsAll(englishSections)
                && englishSections.containsAll(expectedResultsOfTestPage));
    }

}