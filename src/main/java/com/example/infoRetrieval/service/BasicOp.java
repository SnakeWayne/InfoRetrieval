package com.example.infoRetrieval.service;

import com.example.infoRetrieval.pojo.Smoothdou;
import com.example.infoRetrieval.pojo.Smoothsingle;
import com.example.infoRetrieval.pojo.lyricResults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface BasicOp {
    public HashMap<String,ArrayList<String>> andOp(String stra,ArrayList<String> lista,String strb,ArrayList<String> listb);
    public HashMap<String,ArrayList<String>> orOp(String stra,ArrayList<String> lista,String strb,ArrayList<String> listb);
    public HashMap<String,ArrayList<String>> notAndOp(String stra,ArrayList<String> lista,String strb,ArrayList<String> listb);
    public ArrayList<lyricResults> multiAnd(String[] str);
    public ArrayList<lyricResults> multiOr(String[] str);
    public ArrayList<lyricResults> multiNotAnd(String[] str);
    public ArrayList<lyricResults> multiOp(String[] str);
    public HashMap<String,Double> rankPLM(String[] str);
    public Double singleP(String doc,String[] query);//内部方法
    public double getsinglec(String str, List<Smoothsingle> list);//内部方法
    public double getdoublec(Pair pair, List<Smoothdou> list);//内部方法


}
