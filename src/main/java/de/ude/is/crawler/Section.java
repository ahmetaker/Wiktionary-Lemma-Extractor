package de.ude.is.crawler;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class {@code Section} describes the section object
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 1/5/2017.
 */
public class Section {
    /**
     * Section language
     */
    public String language;
    /**
     * Section title
     */
    private String title;
    /**
     * List of {@link Element} for the HTML content of the section
     */
    private List<Element> content;
    /**
     * Set of word forms
     */
    private Set<String> wordsList;

    /**
     * Describes whether this section is lemma form or not
     */
    private boolean lemma;

    public Section(String language, String title, boolean lemma, Set<String> wordsList) {
        this.language = language;
        this.title = title;
        this.lemma = lemma;
        this.wordsList = wordsList;
    }

    public Section() {

    }

    /**
     * Gets HTML content of the section
     *
     * @return {@link List<Element>}
     */
    public List<Element> getContent() {
        return content;
    }

    /**
     * Sets the HTML content of the section
     *
     * @param content
     */
    public void setContent(List<Element> content) {
        this.content = content;
    }

    /**
     * Gets the section's title
     *
     * @return {@link String}
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the Title of the section
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Inserts new HTML element to the section's content
     *
     * @param element
     */
    public void addContent(Element element) {
        if (this.content == null) {
            this.content = new ArrayList<Element>();
        }
        this.content.add(element);
    }

    /**
     * Gets the section's language
     *
     * @return {@link String}
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the section's language
     *
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets the collected word forms of this section
     *
     * @return {@link Set<String>}
     */
    public Set<String> getWordsList() {
        return wordsList;
    }

    /**
     * Sets the word forms
     *
     * @param wordsList
     */
    public void setWordsList(Set<String> wordsList) {
        this.wordsList = wordsList;
    }

    /**
     * Gets the lemma status of this section
     *
     * @return
     */
    public boolean isLemma() {
        return lemma;
    }

    /**
     * Sets the lemma
     *
     * @param lemma
     */
    public void setLemma(boolean lemma) {
        this.lemma = lemma;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getLanguage().equals(((Section) obj).getLanguage())
                && this.isLemma() == ((Section) obj).isLemma()
                && this.getTitle().equals(((Section) obj).getTitle())
                && this.getWordsList().containsAll(((Section) obj).getWordsList())
                && ((Section) obj).getWordsList().containsAll(this.getWordsList());
    }
}
