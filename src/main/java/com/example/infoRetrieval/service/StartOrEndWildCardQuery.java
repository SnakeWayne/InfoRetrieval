package com.example.infoRetrieval.service;

import com.example.infoRetrieval.pojo.lyricResults;

import java.util.List;

public interface StartOrEndWildCardQuery {
    public List<lyricResults> query(String key);
    public List<lyricResults> queryEnd(String key);
}
