package com.planet.obcmobiles.Model;

/**
 * Created by Admin on 11/21/2017.
 */

public class FetchExcel_Response {
    private String Id;
    private String EnglishWord;
    private String HindiWord;
    private String createdDate;

    public FetchExcel_Response(String id, String englishWord, String hindiWord, String createdDate) {
        Id = id;
        EnglishWord = englishWord;
        HindiWord = hindiWord;
        this.createdDate = createdDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEnglishWord() {
        return EnglishWord;
    }

    public void setEnglishWord(String englishWord) {
        EnglishWord = englishWord;
    }

    public String getHindiWord() {
        return HindiWord;
    }

    public void setHindiWord(String hindiWord) {
        HindiWord = hindiWord;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
