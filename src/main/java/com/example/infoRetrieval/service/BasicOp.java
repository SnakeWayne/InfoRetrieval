package com.example.infoRetrieval.service;

import com.example.infoRetrieval.pojo.lyricResults;

import java.util.ArrayList;
import java.util.HashMap;

public interface BasicOp {
    public HashMap<String,ArrayList<String>> addOp(String stra,ArrayList<String> lista,String strb,ArrayList<String> listb);
    public HashMap<String,ArrayList<String>> orOp(String stra,ArrayList<String> lista,String strb,ArrayList<String> listb);
    public HashMap<String,ArrayList<String>> notAndOp(String stra,ArrayList<String> lista,String strb,ArrayList<String> listb);
    public ArrayList<lyricResults> multiOp(String[] str);

}
