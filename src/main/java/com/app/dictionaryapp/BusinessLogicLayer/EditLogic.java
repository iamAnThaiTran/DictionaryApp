package com.app.dictionaryapp.BusinessLogicLayer;

import com.app.dictionaryapp.DataAccessLayer.Database;

public class EditLogic {
    private Database database = new Database("jdbc:mysql://localhost:3306/DictionaryDatabase", "root", "Khongco2004@");

    public boolean insert(String text, String description) {
        database.connectToDatabase();
        String queryGetData = "select * from YourDictionary where word = '" + text + "'";

        if (database.queryGetData(queryGetData) != null) {
            return false;
        } else {
            String queryInsert = "insert into YourDictionary(word, description) value ('" + text + "', '" + description + "')";

            database.queryUpdate(queryInsert);
            return true;
        }
    }

    public boolean delete(String text) {
        database.connectToDatabase();
        String queryGetData = "select * from YourDictionary where word = '" + text + "'";

        if (database.queryGetData(queryGetData) != null) {
            String queryUpdate = "delete from YourDictionary where word = '" + text + "'" ;
            database.queryUpdate(queryUpdate);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(String text, String description) {
        database.connectToDatabase();
        String queryGetData = "select * from YourDictionary where word = '" + text + "'";

        if (database.queryGetData(queryGetData) != null) {
            String queryUpdate = "update YourDictionary set description = '" + description + "' where word = '" + text + "'"  ;
            database.queryUpdate(queryUpdate);
            return true;
        } else {
            return false;
        }
    }
}
