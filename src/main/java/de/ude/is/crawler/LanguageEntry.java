package de.ude.is.crawler;

import de.ude.is.parsesectionsmodel.*;
import de.ude.is.parsesectionsmodel.Section;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code LanguageEntry} represents the content of each language entry
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 2/6/2017.
 */
public class LanguageEntry {
    /**
     * Language name
     */
    private String language;

    /**
     * Wiktionary page title
     */
    private String title;

    /**
     * Entry sections
     */
    private List<ParsingSection> sections;

    public LanguageEntry(String title, String language) {
        this.language = language;
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ParsingSection> getSections() {
        return sections;
    }

    public void setSections(List<ParsingSection> sections) {
        this.sections = sections;
    }

    public void addSection(ParsingSection section) {
        if (this.sections == null) {
            this.sections = new ArrayList<>();
        }
        this.sections.add(section);
    }
}
