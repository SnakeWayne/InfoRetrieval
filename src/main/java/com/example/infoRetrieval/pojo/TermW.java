package com.example.infoRetrieval.pojo;

public class TermW {
    String term;
    String doc;

    public TermW(String term, String doc, double res) {
        this.term = term;
        this.doc = doc;
        this.res = res;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public double getRes() {
        return res;
    }

    public void setRes(double res) {
        this.res = res;
    }

    double res;
}
