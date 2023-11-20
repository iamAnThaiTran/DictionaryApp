package com.app.dictionaryapp.BusinessLogicLayer;

import com.app.dictionaryapp.DataAccessLayer.Txt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecentLogic {
    private Txt txt = new Txt("src/main/resources/com/app/dictionaryapp/PresentationLayer/Txt/Recent.txt");
    private static final RecentLogic instance = new RecentLogic();
    private RecentLogic() {

    }

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

    public ObservableList<String> getContentRecent() {
        ObservableList<String> observableList = txt.getContentInFile();
        FXCollections.reverse(observableList);
        return observableList;
    }

    public static RecentLogic getInstance() {
        return instance;
    }
}
