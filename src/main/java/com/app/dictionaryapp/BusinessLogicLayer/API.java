package com.app.dictionaryapp.BusinessLogicLayer;

public abstract class API {
    private String APIURL;
    private String APIKey;

    public API(String APIURL, String APIKey) {
        this.APIURL = APIURL;
        this.APIKey = APIKey;
    }

    public String getAPIURL() {
        return APIURL;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIURL(String APIURL) {
        this.APIURL = APIURL;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

}
