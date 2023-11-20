package com.app.dictionaryapp.BusinessLogicLayer;

import com.app.dictionaryapp.DataAccessLayer.Database;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditLogic {
    private final Database database = new Database("jdbc:mysql://localhost:3306/DictionaryDatabase", "root", "Khongco2004@");
    private static final EditLogic instance = new EditLogic();

    private EditLogic() {

    }

    public boolean insert(String text, String description) {
        database.connectToDatabase();
        String queryGetData = "select * from YourDictionary where word = '" + text + "'";

        ResultSet resultSet = database.queryGetData(queryGetData);
        try {
            if (resultSet.next()) {
                return false;
            } else {
                String queryInsert = "insert into YourDictionary(word, description) value ('" + text + "', '" + description + "')";
                database.queryUpdate(queryInsert);
                return true;
            }
        } catch (SQLException sqlException) {
            return false;
        }
    }

    public boolean delete(String text) {
        database.connectToDatabase();
        String queryGetData = "select * from YourDictionary where word = '" + text + "'";

        try {
            ResultSet resultSet = database.queryGetData(queryGetData);
            if (resultSet.next()) {
                String queryUpdate = "delete from YourDictionary where word = '" + text + "'" ;
                if (database.queryUpdate(queryUpdate)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(String text, String description) {
        database.connectToDatabase();
        String queryUpdate = "update YourDictionary set description = '" + description + "' where word = '" + text + "'"  ;
        if (database.queryUpdate(queryUpdate)) {
            return true;
        } else {
            return false;
        }
    }

    public static EditLogic getInstance() {
        return instance;
    }
}
