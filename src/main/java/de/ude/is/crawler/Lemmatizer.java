package de.ude.is.crawler;

import de.ude.is.dal.DBService;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lemmatizer Driver
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 1/30/2017
 */
public class Lemmatizer {
    private static int WORKERS = 100;
    public static int QuerySize = 0;
    private static int pageIndex = 0;
    private static AtomicInteger totalProcessed = new AtomicInteger(0);
    private static int languageIndex = 0;
    private static String[] targetedLanguages =
            new String[]{/*"german"*//*, "english",*/ /*"italian",*/ "albanian"/*, "french", "spanish",*/  /*"turkish"*/};
    private static long sTime;
    private static long eTime;

    public static void main(String[] args) throws IOException, InterruptedException {
        Properties prop = new Properties();
        try {
            File jarPath = new File(SessionFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath = jarPath.getParentFile().getAbsolutePath();
            prop.load(new FileInputStream(propertiesPath + "/app.config"));
            WORKERS = Integer.parseInt(prop.getProperty("workers", "100"));
        } catch (IOException e1) {
            System.err.println(e1.getMessage());
            System.out.println("loading default configurations");
            WORKERS = 100;
        }

        if (args.length > 0) targetedLanguages = args;
        long spoint = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(WORKERS);
        DBService.truncateTables();

        processLanguage(pool, targetedLanguages[languageIndex]);

        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long epoint = System.currentTimeMillis();
        long time = epoint - spoint;
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));

        System.out.println("Processing time : " + hms);

    }


    public static void processLanguage(ExecutorService pool, String targetedLanguage) throws IOException {
        WiktionaryLanguagesCrawler languages = WiktionaryLanguagesCrawler.getInstance();
        LangInfo language = languages.search(targetedLanguage);
        if (language != null) {
            System.out.println(String.format("====================%s====================", language.getCanonicalName()));
            System.out.println("fetching pages ...");
            pageIndex = 0;
            LanguageWordsCollector collector = new LanguageWordsCollector(language);
            QuerySize = collector.getListSize();
            System.out.println(String.format("%s pages were found in this category", QuerySize));
            Iterator<String> iterator = collector.iterator();
            System.out.println("processing pages ...");
            sTime = System.currentTimeMillis();
            while (iterator.hasNext()) {
                pageIndex++;
                String pageTitle = iterator.next();
                PageParser parser = new PageParser(pageTitle, language.getCanonicalName());
                parser.setOnCompleteListener(() -> {
                    try {
                        printProgress();
                        if (totalProcessed.get() == QuerySize) {
                            exportToFiles(language);
                            DBService.truncateTables();
                            totalProcessed = new AtomicInteger(0);
                            languageIndex++;
                            if (languageIndex < targetedLanguages.length) {
                                processLanguage(pool, targetedLanguages[languageIndex]);
                            } else {
                                pool.shutdown();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                pool.submit(parser);
            }
        } else {
            System.out.println(String.format("Unable to define language %s", targetedLanguage));
            languageIndex++;
            if (languageIndex < targetedLanguages.length) {
                processLanguage(pool, targetedLanguages[languageIndex]);
            } else {
                pool.shutdown();
            }
        }
    }

    public static void exportToFiles(LangInfo langInfo) {
        System.out.println();
        String[] targetedPOS = new String[]{"Noun", "Verb", "Adjective", "Adverb"};
        for (String pos : targetedPOS) {
            System.out.println("Exporting " + langInfo.getCanonicalName() + " " + pos + "s");
            DBService.exportToFile(langInfo.getCanonicalName(), pos);
        }
    }

    /**
     * Print the progress about the entire process
     */
    private static void printProgress() throws IOException {
        int progressIndex = totalProcessed.incrementAndGet();
        String estimatedTime = "[estimated remaining time: calculating... ]";
        float percentage = ((float) progressIndex / QuerySize);
        if (progressIndex >= 1) {
            eTime = System.currentTimeMillis();
            long estimatedMills = (long) ((eTime - sTime) / percentage);
            estimatedMills = (long) (estimatedMills - (percentage * estimatedMills));
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(estimatedMills),
                    TimeUnit.MILLISECONDS.toMinutes(estimatedMills) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(estimatedMills)),
                    TimeUnit.MILLISECONDS.toSeconds(estimatedMills) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(estimatedMills)));
            estimatedTime = String.format("[estimated remaining time: %s ]", hms);
        }

        String anim = "|/-\\";
        String data = "\r" + anim.charAt(progressIndex % anim.length()) + " " + new DecimalFormat("00.0").format(percentage * 100) + " % " + estimatedTime;
        System.out.write(data.getBytes());
    }


}
