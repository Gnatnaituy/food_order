package com.application.hasaker.DB;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Todo extends LitePalSupport {

    @Column(unique = true, defaultValue = "unknown")
    private String name;

    private int peppery;

    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeppery() {
        return peppery;
    }

    public void setPeppery(int peppery) {
        this.peppery = peppery;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
