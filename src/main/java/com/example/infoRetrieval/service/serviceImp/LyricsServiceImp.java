package com.example.infoRetrieval.service.serviceImp;

import com.example.infoRetrieval.dao.LyricsMapper;
import com.example.infoRetrieval.pojo.Lyrics;
import com.example.infoRetrieval.service.LyricsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LyricsServiceImp implements LyricsService {

    @Resource
    private LyricsMapper lyricsMapper;

    @Override
    public String getLyricsByDoc(String doc) {
        return lyricsMapper.selectByDoc(doc);
    }

    @Override
    public Map<String, String> getLyrics() {

        List<Lyrics> lyrics = (List<Lyrics>) lyricsMapper.selectByAll();
        Map<String, String> lyricsMap = new HashMap<>();

        for (int i = 0; i < lyrics.size(); i++ ) {
            lyricsMap.put(lyrics.get(i).getDoc(), lyrics.get(i).getLyric());
        }

        return lyricsMap;
    }

}
