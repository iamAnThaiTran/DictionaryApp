package com.app.dictionaryapp.BusinessLogicLayer;
import com.app.dictionaryapp.DataAccessLayer.Database;
import com.app.dictionaryapp.DataAccessLayer.Txt;
import javax.xml.parsers.*;

import org.w3c.dom.*;

import java.io.ByteArrayInputStream;
import java.sql.ResultSet;


public class SearchLogic {
    private Database database = new Database("jdbc:mysql://localhost:3306/DictionaryDatabase", "root", "Khongco2004@");

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
//        if (details != null) {
//            try {
//                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                DocumentBuilder builder = factory.newDocumentBuilder();
//                Document doc = builder.parse(new ByteArrayInputStream(details.getBytes("UTF-8")));
//
//                // root element
//                Element root = doc.getDocumentElement();
//
//                // file and access Q tag
//                NodeList qElements = root.getElementsByTagName("Q");
//                Element qElement = (Element) qElements.item(0);
//                String qContent = qElement.getTextContent();
//                String[] lines = qContent.split("\n");
//
//                StringBuilder stringBuilder = new StringBuilder();
//                for (String line : lines) {
//                    stringBuilder.append(line).append("\n");
//                }
//                return stringBuilder.toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        } else {
//            return null;
//        }
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
