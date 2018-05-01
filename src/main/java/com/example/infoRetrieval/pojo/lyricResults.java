package com.example.infoRetrieval.pojo;

public class lyricResults {
    public void setWord(String word) {
        this.word = word;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String word;

    public String getWord() {
        return word;
    }

    public String getFileName() {
        return fileName;
    }

    private String fileName;
}
