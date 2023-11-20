package com.app.dictionaryapp.BusinessLogicLayer;

import com.app.dictionaryapp.DataAccessLayer.Txt;
import javafx.collections.ObservableList;

public class FavoritesLogic {
    private Txt txt = new Txt("src/main/resources/com/app/dictionaryapp/PresentationLayer/Txt/Favorites.txt");
    private static final FavoritesLogic instance = new FavoritesLogic();

    private FavoritesLogic() {

    }

    public ObservableList<String> getContentInFavourite() {
        return txt.getContentInFile();
    }

    public void addTextToFavorite(String text) {
        if (checkTextInFavouriteFile(text)) {
            return;
        } else {
            txt.write(text);
        }
    }

    public void deleteTextInFavorite(String text) {
        txt.deleleTextInFile(text);
    }

    public boolean checkTextInFavouriteFile(String text) {
        ObservableList<String> observableList = getContentInFavourite();
        for (String line: observableList) {
            if (line.equals(text)) {
                return true;
            }
        }
        return false;
    }

    public static FavoritesLogic getInstance() {
        return instance;
    }
}
