package com.example.infoRetrieval.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.infoRetrieval.pojo.RankResult;
import com.example.infoRetrieval.pojo.Raw;
import com.example.infoRetrieval.pojo.lyricResults;
import com.example.infoRetrieval.service.LyricsService;
import com.example.infoRetrieval.service.RetrievalService;
import com.example.infoRetrieval.service.SimilarityCalculate;
import com.example.infoRetrieval.service.StartOrEndWildCardQuery;
import com.example.infoRetrieval.service.serviceImp.BasicOpmp;

import com.example.infoRetrieval.utils.InfixToPostfix;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/InfoRetrieval")
public class LyricController {
    @Resource
    private RetrievalService retrievalService;
    @Resource
    private StartOrEndWildCardQuery wildCardQuery;
    @Resource
    private SimilarityCalculate similarityCalculate;
    @Resource
    private LyricsService lyricsService;
    @Resource
    private BasicOpmp basicOpmp;

    @RequestMapping("/home")
    @ResponseBody
    public String toIndex(HttpServletResponse response) throws IOException {

        System.out.println(similarityCalculate.getSimilarityFromW("my love - 2.txt ", "my love - 2.txt "));
        System.out.println(similarityCalculate.getSimilarityFromWF("my love - 2.txt ", "my love - 2.txt "));

        response.sendRedirect("/index.html");
        return "hello world";
    }

    @RequestMapping("/lyric")
    @ResponseBody
    public ArrayList<lyricResults> getRetrievalResults(@RequestBody String param) {
        JSONObject jo = new JSONObject();
        Map<String, String> m = (Map<String, String> )jo.parse(param);
        String[] postfixStr = InfixToPostfix.convertTo(m.get("keywords"));
        return basicOpmp.multiOp(postfixStr);
    }

    @RequestMapping("/wildCardSearch")
    @ResponseBody
    public List<Raw> wildCardSearch(@RequestBody String param) {

        //解析参数，并转成大写
        JSONObject jo = new JSONObject();
        Map<String, String> m = (Map<String, String> )jo.parse(param);
        String keys = m.get("keywords");
        keys = keys.toUpperCase();

        //分通配符的类型分别进行处理
        int index = keys.indexOf("*");
        if (index == 0) {
            keys = keys.substring(1, keys.length()) + "$";
        }
        else if (index != keys.length() - 1) {
            String ho = keys.substring(index + 1, keys.length());
            String pre = keys.substring(0, index);
            keys = ho + "$" + pre;
        }
        if (index == keys.length() - 1) {
            String temp = keys.substring(0, keys.length() - 1);
            keys = temp ;
            List<lyricResults> res = wildCardQuery.queryEnd(keys);
            List<String> terms = new ArrayList<>();
            for (int i = 0; i < res.size(); i++) {
                terms.add(res.get(i).getFileName());
            }
            return retrievalService.getDocLocation(terms);
        }
        int lastIndex = keys.lastIndexOf("*");
        if (lastIndex != index) {
            String temp = keys.substring(0, keys.length() - 2);
            keys = temp;
        }



        List<lyricResults> res = wildCardQuery.query(keys);
        List<String> terms = new ArrayList<>();
        for (int i = 0; i < res.size(); i++) {
            terms.add(res.get(i).getFileName());
        }


        return retrievalService.getDocLocation(terms);
    }

    @RequestMapping("/similarity")
    @ResponseBody
    public List<Double> docSimilarity(@RequestBody String param) {

        //解析参数
        JSONObject jo = new JSONObject();
        Map<String, String> m = (Map<String, String> )jo.parse(param);

        String doc1 = m.get("doc1");
        String doc2 = m.get("doc2");

        double w = similarityCalculate.getSimilarityFromW(doc1, doc2);
        double wf = similarityCalculate.getSimilarityFromWF(doc1, doc2);

        List<Double> re = new ArrayList<>(2);
        re.add(w);
        re.add(wf);

        return re;
    }


    @RequestMapping("/plmSearch")
    @ResponseBody
    public List<RankResult> PLMSearch(@RequestBody String param) {

        //解析参数，并转成大写
        JSONObject jo = new JSONObject();
        Map<String, String> m = (Map<String, String> )jo.parse(param);
        String keys = m.get("keywords");
        keys = keys.toUpperCase();

        String[] searchToken = keys.split(" ");

        List<RankResult> rankResults = new ArrayList<>();

        if (searchToken.length > 0) {

            Map<String, String> lyrics = lyricsService.getLyrics();

            HashMap<String, Double> plm = basicOpmp.goodrankPLM(searchToken);
            Iterator iter = plm.entrySet().iterator();


            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if ((double)entry.getValue() == 0)
                    continue;
                rankResults.add(new RankResult(entry.getKey(), entry.getValue(), lyrics.get(entry.getKey())));
            }

            //排序比较器
            Comparator c = new Comparator<RankResult>() {
                @Override
                public int compare(RankResult o1, RankResult o2) {
                    if (o1.getRank() < o2.getRank())
                        return 1;
                    else
                        return -1;
                }
            };
            rankResults.sort(c);
        }


        return rankResults;
    }
}

