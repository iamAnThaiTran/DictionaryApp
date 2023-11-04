package com.app.dictionaryapp.BusinessLogicLayer;

import com.app.dictionaryapp.DataAccessLayer.Database;

public class EditLogic {
    private Database database = new Database("jdbc:mysql://localhost:3306/DictionaryDatabase", "root", "Khongco2004@");

    public void insert(String text, String description) {
        String queryInsert = "insert into YourDictionary(word, description) value ('" + text + "', '" + description + "')";

        database.queryDDL(queryInsert);
    }

    public void delete(String text) {

    }
}
