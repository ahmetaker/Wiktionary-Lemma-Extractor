package de.ude.is.crawler;

import org.jsoup.nodes.Document;

/**
 * Created by firas on 2/17/2017.
 */
public class ParsingSection {
    private int index;
    private String title;
    private Document document;

    public ParsingSection(int index, String title, Document document) {
        this.index = index;
        this.title = title;
        this.document = document;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
