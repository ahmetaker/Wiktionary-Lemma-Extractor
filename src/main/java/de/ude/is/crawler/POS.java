package de.ude.is.crawler;

import java.util.HashSet;
import java.util.Set;

/**
 * Enum {@code POS} presents different types of 'part of speech'.
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 1/7/2017.
 */
public enum POS {
    VERB("VERB".toUpperCase()),
    ADJECTIVE("ADJECTIVE".toUpperCase()),
    ADVERB("ADVERB".toUpperCase()),
    NOUN("NOUN".toUpperCase());

    public static final Set<String> TARGETED_POS = new HashSet<String>() {{
        add(POS.VERB.toString());
        add(POS.ADJECTIVE.toString());
        add(POS.NOUN.toString());
        add(POS.ADVERB.toString());
    }};

    /**
     * The name of part of speech
     */
    private final String name;

    POS(String name) {
        this.name = name;
    }
}
