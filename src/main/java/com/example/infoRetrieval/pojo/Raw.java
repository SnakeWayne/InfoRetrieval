package com.example.infoRetrieval.pojo;

public class Raw {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column raw.term
     *
     * @mbggenerated Tue May 01 12:50:13 CST 2018
     */
    private String term;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column raw.doc
     *
     * @mbggenerated Tue May 01 12:50:13 CST 2018
     */
    private String doc;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column raw.term
     *
     * @return the value of raw.term
     *
     * @mbggenerated Tue May 01 12:50:13 CST 2018
     */
    public String getTerm() {
        return term;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column raw.term
     *
     * @param term the value for raw.term
     *
     * @mbggenerated Tue May 01 12:50:13 CST 2018
     */
    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column raw.doc
     *
     * @return the value of raw.doc
     *
     * @mbggenerated Tue May 01 12:50:13 CST 2018
     */
    public String getDoc() {
        return doc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column raw.doc
     *
     * @param doc the value for raw.doc
     *
     * @mbggenerated Tue May 01 12:50:13 CST 2018
     */
    public void setDoc(String doc) {
        this.doc = doc == null ? null : doc.trim();
    }
}