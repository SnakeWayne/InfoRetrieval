package com.example.infoRetrieval.pojo;

public class WfTbl {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wf_tbl.term
     *
     * @mbggenerated Sat Jun 02 13:55:56 GMT+08:00 2018
     */
    private String term;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wf_tbl.doc
     *
     * @mbggenerated Sat Jun 02 13:55:56 GMT+08:00 2018
     */
    private String doc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wf_tbl.res
     *
     * @mbggenerated Sat Jun 02 13:55:56 GMT+08:00 2018
     */
    private Double res;

    public WfTbl(String term, String doc, double v) {
        this.term = term;
        this.doc = doc;
        this.res = v;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wf_tbl.term
     *
     * @return the value of wf_tbl.term
     *
     * @mbggenerated Sat Jun 02 13:55:56 GMT+08:00 2018
     */
    public String getTerm() {
        return term;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wf_tbl.term
     *
     * @param term the value for wf_tbl.term
     *
     * @mbggenerated Sat Jun 02 13:55:56 GMT+08:00 2018
     */
    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wf_tbl.doc
     *
     * @return the value of wf_tbl.doc
     *
     * @mbggenerated Sat Jun 02 13:55:56 GMT+08:00 2018
     */
    public String getDoc() {
        return doc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wf_tbl.doc
     *
     * @param doc the value for wf_tbl.doc
     *
     * @mbggenerated Sat Jun 02 13:55:56 GMT+08:00 2018
     */
    public void setDoc(String doc) {
        this.doc = doc == null ? null : doc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wf_tbl.res
     *
     * @return the value of wf_tbl.res
     *
     * @mbggenerated Sat Jun 02 13:55:56 GMT+08:00 2018
     */
    public Double getRes() {
        return res;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wf_tbl.res
     *
     * @param res the value for wf_tbl.res
     *
     * @mbggenerated Sat Jun 02 13:55:56 GMT+08:00 2018
     */
    public void setRes(Double res) {
        this.res = res;
    }
}