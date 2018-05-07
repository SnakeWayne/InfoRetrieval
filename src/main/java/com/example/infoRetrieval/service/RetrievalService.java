package com.example.infoRetrieval.service;

import com.example.infoRetrieval.pojo.Music;
import com.example.infoRetrieval.pojo.Raw;
import com.example.infoRetrieval.pojo.lyricResults;

import java.util.List;

public interface RetrievalService {

    public List<Raw> getDocLocation(List<String> keywords);
}
