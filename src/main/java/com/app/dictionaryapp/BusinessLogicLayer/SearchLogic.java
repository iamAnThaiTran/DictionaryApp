package com.app.dictionaryapp.BusinessLogicLayer;
import com.app.dictionaryapp.DataAccessLayer.Cache;
import com.app.dictionaryapp.DataAccessLayer.Database;
import java.sql.ResultSet;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;


public class SearchLogic {
    private final Database database = new Database("jdbc:mysql://localhost:3306/DictionaryDatabase", "root", "Khongco2004@");
    private final Cache cache = new Cache();

    public String getDetail(String text) {
        ResultSet resultSet = database.queryGetData("select description from av where word = '" + text + "'");

        try {
            if (resultSet.next()) {
                return resultSet.getString("description");
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getHtml(String text) {
        ResultSet resultSet1 = database.queryGetData("select html from av where word = '" + text + "'");
        ResultSet resultSet2 = database.queryGetData("select description from YourDictionary where word='" + text + "'");

        try {
            if (resultSet1.next()) {
                Document document = Jsoup.parse(resultSet1.getString("html"));

                Elements h1 = document.select("h1");
                Elements h3 = document.select("h3");

                h1.remove();
                h3.remove();


                return document.toString();
            } else {
                return resultSet2.getString("description");
            }
        } catch (Exception e) {
            return "";
        }

    }
    public String getPronounciation(String text) {
        ResultSet resultSet = database.queryGetData("select pronounce from av where word = '" + text + "'");
        try {
            if (resultSet.next()) {
                return resultSet.getString("pronounce");
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
