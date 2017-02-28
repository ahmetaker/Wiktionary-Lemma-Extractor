package de.ude.is.crawler;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class {@code URLReader} reads the HTML content of a url
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 1/30/2017.
 */
public class URLReader {

    /**
     * URL to read
     */
    private final String url;

    public URLReader(String url) {
        this.url = url;
    }

    /**
     * Reads the content of a web page using its url
     *
     * @return {@link String} HTML of a web page
     * @throws IOException
     */
    public String read() throws IOException {
        InputStream in = new URL(this.url).openStream();
        String out = null;
        try {
            out = IOUtils.toString(in);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return out;
    }
}
