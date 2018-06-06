package com.example.infoRetrieval.service;


import java.util.Map;

public interface LyricsService {
    public String getLyricsByDoc(String doc);
    public Map<String, String> getLyrics();
}
