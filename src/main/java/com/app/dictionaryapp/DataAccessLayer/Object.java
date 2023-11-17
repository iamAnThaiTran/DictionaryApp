package com.app.dictionaryapp.DataAccessLayer;
public class Object {
    private String searchWord_directory = "src/main/resources/com/app/dictionaryapp/PresentationLayer/Txt/Recent.txt";
    private String favourite_directory = "src/main/resources/com/app/dictionaryapp/PresentationLayer/Txt/Favorites.txt";
    private String darkMode = getClass().getResource("/com/app/dictionaryapp/PresentationLayer/DarkMode.css").toExternalForm();
    private String lightMode = getClass().getResource("/com/app/dictionaryapp/PresentationLayer/LightMode.css").toExternalForm();
    public static boolean mouseMove;
    public static boolean RecentScene;
    public static boolean FavouriteScene;
    public static boolean AlertScene = false;
    public String getFavourite_directory() {
        return favourite_directory;
    }
    public String getSearchWord_directory() {
        return searchWord_directory;
    }

    public String getLightMode() {
        return lightMode;
    }

    public String getDarkMode() {
        return darkMode;
    }
}
