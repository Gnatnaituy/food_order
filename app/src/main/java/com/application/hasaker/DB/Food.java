package com.application.hasaker.DB;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Food extends LitePalSupport {

    @Column(unique = true, defaultValue = "unknown", nullable = false)
    private String name;

    private int id;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
