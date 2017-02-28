package de.ude.is.crawler;

/**
 * Enum {@code Selectors} presents the needed selectors used in the parsing process
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 1/27/2017.
 */
public enum Selectors {
    /**
     * Headline class selector as defined in Wiktionary
     */
    HEADLINE_CLASS(".mw-headline"),

    /**
     * Headline class name
     */
    HEADLINE("mw-headline"),

    /**
     * New line
     */
    NEWLINE("\\r?\\n"),

    /**
     * Wiktionary table class selector
     */
    WIKI_TABLE(".wikitable"),

    /**
     * Table of content class selector
     */
    TOC(".toc"),

    /**
     * Indicates to a related article in another Wikimedia platform
     */
    SISTER_PROJECT(".sister-wikipedia"),

    /**
     * Edit section class selector
     */
    EDIT_SECTION(".mw-editsection");

    /**
     * Selector's code
     */
    private final String code;

    Selectors(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

}
