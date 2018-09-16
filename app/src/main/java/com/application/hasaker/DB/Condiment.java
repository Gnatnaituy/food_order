package com.application.hasaker.DB;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Condiment extends LitePalSupport {

    @Column(unique = true, defaultValue = "unknown", nullable = false)
    private int id;

    private String Name;

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
