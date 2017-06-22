package com.geoffledak.signupapp.model;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by geoff on 6/21/17.
 */

@Parcel
public class SearchResult {

    private String title;
    private String link;
    private String description;
    private String modified;
    private String generator;
    private List<ImageItem> items;


    public SearchResult() { }

    public SearchResult( String title, String link, String description, String modified, String generator, List<ImageItem> items ) {

        this.title = title;
        this.link = link;
        this.description = description;
        this.modified = modified;
        this.generator = generator;
        this.items = items;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public List<ImageItem> getItems() {
        return items;
    }

    public void setItems(List<ImageItem> items) {
        this.items = items;
    }


}
