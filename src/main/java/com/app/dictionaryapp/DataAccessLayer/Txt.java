package com.app.dictionaryapp.DataAccessLayer;

import java.io.File;

public class Txt {
    private String name;
    private File file;

    public Txt(String name) {
        this.name = name;
        file = new File(name);
    }
}
