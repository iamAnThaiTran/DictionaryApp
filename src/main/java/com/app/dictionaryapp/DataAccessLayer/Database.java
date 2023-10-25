package com.app.dictionaryapp.DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database {
    private String URL;
    private String USER;
    private String PASSWORD;
    private Connection connection;

    public Database(String URL, String USER, String PASSWORD) {
        this.URL = URL;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getURL() {
        return URL;
    }

    public String getUSERNAME() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnectToDatabase() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * querySearch to get data from database
     * @param text : condition.
     * @param colName : collumn name(word, detail)
     * @return String.
     */
    public String querySearch(String text, String colName) {
        // ket noi den database
        connectToDatabase();

        String words = "";
        String details = "";
        try {
            // tao statement de thuc thi query
            Statement statement = connection.createStatement();

            // tao resultset de luu ket qua tu cau query
            ResultSet resultSetDetail = statement.executeQuery("select detail from tbl_edict where word = '" + text + "'");
            if (resultSetDetail.next()) {
                details += resultSetDetail.getString("detail");
            }

            ResultSet resultSetWord = statement.executeQuery("select word from tbl_edict where word like '" + text + "%" + "'");
            while (resultSetWord.next()) {
                words += resultSetWord.getString("word");
            }

            // giai phong resultSet
            resultSetDetail.close();
            resultSetWord.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (colName.equals("word")) {
            return words;
        } else if (colName.equals("detail")) {
            return details;
        } else {
            return "";
        }
    }
}
