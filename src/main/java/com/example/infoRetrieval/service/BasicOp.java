package com.example.infoRetrieval.service;

import com.example.infoRetrieval.pojo.*;

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
    public HashMap<String,Double> goodrankPLM(String[] str);//本次新写的方法 调用一下
    public Double goodsingleP(String doc,String[] query,HashMap<String,Double> a,HashMap<String,Double> goodexistsingle,HashMap<Pair,Double> goodexistdou);//内部方法
    public Double singleP(String doc,String[] query);//内部方法
   // public double getsinglec(String str, List<Smoothsingle> list);//内部方法
    //public double getdoublec(Pair pair, List<Smoothdou> list);//内部方法
    public double getsinglec(String str, List<BeforeIndex> list);//内部方法
    public double getdoublec(Pair pair, List<Countdou> list);//内部方法


}
