package com.example.infoRetrieval.pojo;

public class RankResult {
    String doc;

    public RankResult(Object key, Object value) {
        doc = (String) key;
        rank = (double) value;
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
