package de.ude.is.crawler;

import java.io.Serializable;

/**
 * Class {@code LangInfo} represents a Wiktionary language
 * <p> see <a href="https://en.wiktionary.org/wiki/Category:All_languages">Wiktionary languages</a> for more information
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 1/24/2017
 */
public class LangInfo implements Serializable {
    /**
     * Full name of the language
     */
    private String canonicalName;

    /**
     * Language code e.g en, de
     */
    private String languageCode;

    /**
     * Language family
     */
    private String languageFamily;

    /**
     * Language ancestors
     */
    private String ancestors;

    /**
     * Character set of this language e.g: Lain, Hindi, Han, Arab
     */
    private String scripts;

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageFamily() {
        return languageFamily;
    }

    public void setLanguageFamily(String languageFamily) {
        this.languageFamily = languageFamily;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getScripts() {
        return scripts;
    }

    public void setScripts(String scripts) {
        this.scripts = scripts;
    }
}
