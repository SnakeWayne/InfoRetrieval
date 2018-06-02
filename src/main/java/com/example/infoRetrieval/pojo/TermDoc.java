package com.example.infoRetrieval.pojo;


//文档的df
public class TermDoc {

    public String getTerm() {
        return term;
    }

    public int getDocNum() {
        return docNum;
    }

    private String term;

    public void setTerm(String term) {
        this.term = term;
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
    }

    private int docNum;
}
