package com.example.infoRetrieval.controller;


import com.example.infoRetrieval.service.RetrievalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/InfoRetrieval")
public class LyricController {
    @Resource
    private RetrievalService retrievalService;

    @RequestMapping("/home")
    @ResponseBody
    public String toIndex(){
        return "hello world";
    }

}

