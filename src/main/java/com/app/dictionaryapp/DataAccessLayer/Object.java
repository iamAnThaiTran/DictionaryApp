package com.app.dictionaryapp.DataAccessLayer;
public class Object {
    private final String searchWord_directory = "src/main/resources/com/app/dictionaryapp/PresentationLayer/Txt/Recent.txt";
    private final String favourite_directory = "src/main/resources/com/app/dictionaryapp/PresentationLayer/Txt/Favorites.txt";
    private final String darkMode = getClass().getResource("/com/app/dictionaryapp/PresentationLayer/DarkMode.css").toExternalForm();
    private final String lightMode = getClass().getResource("/com/app/dictionaryapp/PresentationLayer/LightMode.css").toExternalForm();
    private final String darkModeWebView = getClass().getResource("/com/app/dictionaryapp/PresentationLayer/StyleWebView.css").toExternalForm();
    private final String lightModeWebView = getClass().getResource("/com/app/dictionaryapp/PresentationLayer/LightModeWebView.css").toExternalForm();
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

    public String getDarkModeWebView() {
        return darkModeWebView;
    }

    public String getLightModeWebView() {
        return lightModeWebView;
    }
}
