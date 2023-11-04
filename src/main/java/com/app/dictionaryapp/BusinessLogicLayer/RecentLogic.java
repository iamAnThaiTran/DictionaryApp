package com.app.dictionaryapp.BusinessLogicLayer;

import com.app.dictionaryapp.DataAccessLayer.Txt;
import javafx.collections.ObservableList;

public class RecentLogic {
    private Txt txt = new Txt("src/main/resources/com/app/dictionaryapp/PresentationLayer/Txt/Recent.txt");

    public void addRecentWord(String text) {
        txt.connect(); // connect
        ObservableList<String> lines = txt.getContentInFile(); // get content
        for(String line : lines) {
            if (line.equals(text)) {
                txt.deleleTextInFile(line);
            }
        }
        txt.write(text);
    }

}
