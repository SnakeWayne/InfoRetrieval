package com.example.infoRetrieval.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.infoRetrieval.pojo.lyricResults;
import com.example.infoRetrieval.service.RetrievalService;
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
import java.util.Map;

@Controller
@RequestMapping("/InfoRetrieval")
public class LyricController {
    @Resource
    private RetrievalService retrievalService;

    @Resource
    private BasicOpmp basicOpmp;

    @RequestMapping("/home")
    @ResponseBody
    public String toIndex(HttpServletResponse response) throws IOException {
        response.sendRedirect("/static/index.html");
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

}

