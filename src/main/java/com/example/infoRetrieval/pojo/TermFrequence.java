package com.example.infoRetrieval.pojo;

public class TermFrequence {
    private String term;

    public String getTerm() {
        return term;
    }

    public String getDoc() {
        return doc;
    }

    public int getFeq() {
        return freq;
    }

    private String doc;

    public void setTerm(String term) {
        this.term = term;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public void setFeq(int feq) {
        this.freq = feq;
    }

    private int freq;
}
