package de.ude.is.crawler;

import com.google.gson.Gson;
import de.ude.is.parsepagemodel.ParsePageAPI;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Class {@code PageFetcher} fetches Wiktionary page content
 * <p> see <a href="https://en.wiktionary.org/w/api.php?action=parse&prop=text&format=xml&page=test">API example</a> for page test
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 2/3/2017
 */
public class PageFetcher {
    /**
     * Parse API to fetch page content
     */
    private static final String PARSE_API = "https://en.wiktionary.org/w/api.php" +
            "?action=parse" +
            "&prop=text" +
            "&page=%s" +
            "&format=json";
    private static final Logger LOGGER = Logger.getLogger(PageFetcher.class.getName());
    /**
     * Page title
     */
    private final String title;
    /**
     * Section index
     */
    private String section;

    /**
     * Constructor to fetch the whole page
     *
     * @param title page title
     */
    public PageFetcher(String title) {
        this.title = title;
        this.section = "";
    }

    /**
     * Constructor to fetch section in a page
     *
     * @param title   page title
     * @param section section index
     */
    public PageFetcher(String title, String section) {
        this.title = title;
        this.section = section;
    }

    /**
     * Get the HTML content of a page
     *
     * @return HTML string
     * @throws IOException
     */
    public String fetchPage() throws IOException {
        String json = null;
        String url = String.format(PARSE_API, URLEncoder.encode(this.title, "UTF-8"));
        try {
            json = new URLReader(url).read();
        } catch (Exception ex) {
            LOGGER.log(Level.ERROR, "unable to read: " + url, ex);
        }
        Gson gson = new Gson();
        ParsePageAPI parsePageAPI = gson.fromJson(json, ParsePageAPI.class);
        String html = parsePageAPI.getParse().getText().getHtml();
        String formattedHtml = html.toString().replaceAll(">", ">\n").replaceAll("<", "\n<");
        return formattedHtml;
    }
}
