package com.app.dictionaryapp.BusinessLogicLayer;

public class Data {
    private int idx;
    private String word;
    private String detail;
    private String dateTime;


    public int getIdx() {
        return idx;
    }

    public String getWord() {
        return word;
    }

    public String getDetail() {
        return detail;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
