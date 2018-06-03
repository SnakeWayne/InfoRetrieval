package com.example.infoRetrieval.service.serviceImp;

import com.example.infoRetrieval.dao.RawMapper;
import com.example.infoRetrieval.dao.WTblMapper;
import com.example.infoRetrieval.dao.WfTblMapper;
import com.example.infoRetrieval.pojo.*;
import com.example.infoRetrieval.service.SimilarityCalculate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SimilarityCalculateImp implements SimilarityCalculate {

    @Resource
    private RawMapper rawMapper;
    @Resource
    private WTblMapper wTblMapper;
    @Resource
    private WfTblMapper wfTblMapper;
//    @Resource
//    private WFMapper wfMapper;
//    @Resource
//    private WMapper wMapper;

    @Override
    public List<TermDoc> getDF() {
        return rawMapper.getDocNum();
    }

    @Override
    public List<TermFrequence> getTF() {
        return rawMapper.getTermFreq();
    }

    @Override
    //使用wf，对tf归一化
    public List<WfTbl> calculateW2() {
        List<TermFrequence> tFList = getTF();
        Map<String, Double> idfs = calculateIDF();
        List<WfTbl> wList = new ArrayList<>();

        for (int i = 0; i < tFList.size(); i++) {
            TermFrequence tf = tFList.get(i);

            WfTbl re = new WfTbl(tf.getTerm(), tf.getDoc(), tf.getFeq() * idfs.get(tf.getTerm()));

            //wfTblMapper.insert(re);
            wList.add(re);
        }

        return wList;
    }

    @Override
    //使用tf，未对tf归一化
    public List<WTbl> calculateW1() {
        List<TermFrequence> tFList = getTF();
        Map<String, Double> idfs = calculateIDF();
        List<WTbl> wList = new ArrayList<>();

        for (int i = 0; i < tFList.size(); i++) {
            TermFrequence tf = tFList.get(i);
            double freq = tf.getFeq();
            freq = ( freq == 0 ? 0 : 1 + Math.log(freq));
            WTbl re = new WTbl(tf.getTerm(), tf.getDoc(), freq * idfs.get(tf.getTerm()));
            //wTblMapper.insert(re);
            wList.add(re);
        }

        return wList;
    }

    @Override
    public Map<String, Double> calculateIDF() {

        List<TermDoc> dfs = getDF();
        Map<String, Double> idfs = new HashMap<>();

        int docNum = rawMapper.getDocCount();

        for (int i = 0; i < dfs.size(); i++) {
            TermDoc df = dfs.get(i);
           idfs.put(df.getTerm(), Math.log( ((double) docNum) / ((double)df.getDocNum())));
        }

        return idfs;
    }


    @Override
    public double getSimilarityFromWF(String doc1, String doc2) {
        List<WfTbl> wfs1 = wfTblMapper.selectByDoc(doc1);
        List<WfTbl> wfs2 = wfTblMapper.selectByDoc(doc2);

        HashMap<String, Double> hm2 = new HashMap<>(wfs2.size(), 1);

        double wf1Mod = 0;
        double wf2Mod = 0;

        //计算两者的wf向量模长
        for (int i = 0; i < wfs1.size(); i++) {
            //hm1.put(wfs1.get(i).getDoc(), wfs1.get(i).getRes());
            wf1Mod += Math.pow(wfs1.get(i).getRes(), 2);
        }
        for (int i = 0; i < wfs2.size(); i++) {
            hm2.put(wfs2.get(i).getTerm(), wfs2.get(i).getRes());//将文档2加入一个hashmap
            wf2Mod += Math.pow(wfs2.get(i).getRes(), 2);
        }
        wf1Mod = Math.sqrt(wf1Mod);
        wf2Mod = Math.sqrt(wf2Mod);

        //计算内积
        double inMultiply = 0;
        for (int i =0; i < wfs1.size(); i++) {
            double wf1 = wfs1.get(i).getRes();
            String doc = wfs1.get(i).getTerm();
            //看是否第二个文档有这个词，有则计算，否则无需计算。对于第一个文档没有的词，无需计算。
            if (hm2.containsKey(doc)) {
                inMultiply += wf1 * hm2.get(doc);
            }
        }

        return inMultiply / (wf1Mod * wf2Mod);
    }

    public double getSimilarityFromW(String doc1, String doc2) {
        List<WTbl> ws1 = wTblMapper.selectByDoc(doc1);
        List<WTbl> ws2 = wTblMapper.selectByDoc(doc2);

        HashMap<String, Double> hm2 = new HashMap<>(ws2.size(), 1);

        double wf1Mod = 0;
        double wf2Mod = 0;

        //计算两者的wf向量模长
        for (int i = 0; i < ws1.size(); i++) {
            //hm1.put(wfs1.get(i).getDoc(), wfs1.get(i).getRes());
            wf1Mod += Math.pow(ws1.get(i).getRes(), 2);
        }
        for (int i = 0; i < ws2.size(); i++) {
            hm2.put(ws2.get(i).getTerm(), ws2.get(i).getRes());//将文档2加入一个hashmap
            wf2Mod += Math.pow(ws2.get(i).getRes(), 2);
        }
        wf1Mod = Math.sqrt(wf1Mod);
        wf2Mod = Math.sqrt(wf2Mod);

        //计算内积
        double inMultiply = 0;
        for (int i =0; i < ws1.size(); i++) {
            double w1 = ws1.get(i).getRes();
            String doc = ws1.get(i).getTerm();
            //看是否第二个文档有这个词，有则计算，否则无需计算。对于第一个文档没有的词，无需计算。
            if (hm2.containsKey(doc)) {
                inMultiply += w1 * hm2.get(doc);
            }
        }

        return inMultiply / (wf1Mod * wf2Mod);
    }
}
