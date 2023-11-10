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
     * query method.
     * @param query: String.
     * @return ResultSet
     */
    public ResultSet queryGetData(String query) {
        // ket noi den database
        connectToDatabase();

        try {
            // tao statement de thuc thi query
            Statement statement = connection.createStatement();

            // tao ResuleSet
            ResultSet resultSet = statement.executeQuery(query);

            return resultSet;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean queryUpdate(String query) {
        connectToDatabase();

        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
