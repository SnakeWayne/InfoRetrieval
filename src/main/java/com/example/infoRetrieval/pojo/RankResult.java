package com.example.infoRetrieval.pojo;

public class RankResult {
    String doc;

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    String lyric;

    public RankResult(Object key, Object value, Object l) {
        doc = (String) key;
        rank = (double) value;
        lyric = (String) l;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    double rank;

}
