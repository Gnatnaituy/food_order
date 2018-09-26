package com.application.hasaker.DB;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Todo extends LitePalSupport {

    @Column(unique = true, defaultValue = "unknown")
    private int id;

    private String name;

//    private String peppery;

    private String count;

    private String condiment;

    private int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getPeppery() {
//        return peppery;
//    }
//
//    public void setPeppery(String peppery) {
//        this.peppery = peppery;
//    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCondiment() {
        return condiment;
    }

    public void setCondiment(String condiment) {
        this.condiment = condiment;
    }
}
