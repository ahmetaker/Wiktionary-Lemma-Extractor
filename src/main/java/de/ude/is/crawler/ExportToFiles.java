package de.ude.is.crawler;

import de.ude.is.dal.DBService;

/**
 * Created by firas on 1/23/2017.
 */
public class ExportToFiles {
    public static void main(String[] args) {
        String[] targetedLanguages = new String[]{/*"German", "English", */"Italian"/*, "Dutch", "French", "Spanish", "Turkish"*/};
        for (String language : targetedLanguages) {
            String[] targetedPOS = new String[]{"Noun", "Verb", "Adjective", "Adverb"};
            for (String pos : targetedPOS) {
                System.out.println("Exporting " + language + "_" + pos + ".txt");
                DBService.exportToFile(language, pos);
            }
        }

    }
}
