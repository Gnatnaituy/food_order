package com.application.hasaker.DB;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Condiment extends LitePalSupport {

    @Column(unique = true, defaultValue = "unknown", nullable = false)
    private int id;

    private String tagName;

    public int getId() {
        return id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
