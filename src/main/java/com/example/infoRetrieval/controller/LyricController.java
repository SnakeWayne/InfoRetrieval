package com.example.infoRetrieval.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.infoRetrieval.pojo.Raw;
import com.example.infoRetrieval.pojo.lyricResults;
import com.example.infoRetrieval.service.RetrievalService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/InfoRetrieval")
public class LyricController {
    @Resource
    private RetrievalService retrievalService;
    @Resource
    private StartOrEndWildCardQuery wildCardQuery;



    @Resource
    private BasicOpmp basicOpmp;

    @RequestMapping("/home")
    @ResponseBody
    public String toIndex(HttpServletResponse response) throws IOException {
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
        JSONObject jo = new JSONObject();
        Map<String, String> m = (Map<String, String> )jo.parse(param);
        String keys = m.get("keywords");
        keys = keys.toUpperCase();
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

}

