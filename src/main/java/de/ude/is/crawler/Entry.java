package de.ude.is.crawler;

import de.ude.is.crawler.Section;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;

/**
 * Class {@code Entry} represents Wiktionary word
 * <p> Each word in Wiktionary is represented in language, sections and disambiguation related words
 * sections include parts of speech(noun, verb, ...etc), translations, pronunciation and others
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 12/17/2016
 */
public class Entry {
    private String language;
    private List<Section> sections;
    private List<String> disambiguationRelatedWords;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<String> getDisambiguationRelatedWords() {
        return disambiguationRelatedWords;
    }

    public void setDisambiguationRelatedWords(List<String> disambiguationRelatedWords) {
        this.disambiguationRelatedWords = disambiguationRelatedWords;
    }
}
