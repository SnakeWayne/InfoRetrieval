package com.example.infoRetrieval.service;

import com.example.infoRetrieval.pojo.*;

import java.util.List;
import java.util.Map;

public interface SimilarityCalculate {
    public List<TermDoc> getDF();
    public List<TermFrequence> getTF();
    public List<WTbl> calculateW1();
    public List<WfTbl> calculateW2();
    public Map<String, Double> calculateIDF();
    public double getSimilarityFromWF(String doc1, String doc2);
    public double getSimilarityFromW(String doc1, String doc2);
}
