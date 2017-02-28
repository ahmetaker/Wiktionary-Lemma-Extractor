package de.ude.is.crawler;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

/**
 * Class {@code TitlesDumpDownloader} downloads the Wiktionary titles dump
 * <p> see <a href="https://dumps.wikimedia.org/enwiktionary/latest/">Latest dumps</a>
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 1/20/2017.
 */
public class TitlesDumpDownloader {
    /**
     * latest dumps url
     */
    private static final String LATESET_DUMPS_URL = "https://dumps.wikimedia.org/enwiktionary/latest/";

    /**
     * saved dump file
     */
    private static final String DUMP_FILE_NAME = "enwiktionary-latest-all-titles-in-ns0.gz";

    /**
     * dumps directory
     */
    private static final String DUMPS_DIRECTORY = "dumps/";

    /**
     * dump file postfix
     */
    private static final String FILE_POSTFIX = ".txt.gz";

    /**
     * Discover and download the latest version of titles dump file
     *
     * @return {@link File}
     * @throws IOException
     */
    public File downloadTheLatestDump() throws IOException {
        Document doc = Jsoup.connect(LATESET_DUMPS_URL).get();
        Elements dumpFiles = doc.select("pre");
        int dumpIndex = -1;
        for (Element dump : dumpFiles.get(0).children()) {
            if (dump.text().equals(DUMP_FILE_NAME)) {
                dumpIndex = dump.siblingIndex();
                break;
            }
        }

        if (dumpIndex != -1) {
            String dumpDateAndSize = dumpFiles.get(0).childNode(dumpIndex + 1).toString().trim();
            String date = dumpDateAndSize.split(" ")[0] + " " + dumpDateAndSize.split(" ")[1];
            String fileName = date.replaceAll(":", ".");
            File dumpFile = new File(DUMPS_DIRECTORY + fileName + FILE_POSTFIX);
            if (!dumpFile.exists()) {
                System.out.println("Downloading the latest dump ...");
                new java.io.File(DUMPS_DIRECTORY).mkdirs();
                FileUtils.copyURLToFile(new URL(LATESET_DUMPS_URL + DUMP_FILE_NAME), dumpFile);
                System.out.println("Download completed");
            } else {
                System.out.println("You have the latest dump version");
            }
            return dumpFile;

        }
        return null;
    }

}
