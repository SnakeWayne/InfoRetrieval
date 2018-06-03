package com.example.infoRetrieval.service.serviceImp;

import com.example.infoRetrieval.dao.RawMapper;
import com.example.infoRetrieval.pojo.Raw;
import com.example.infoRetrieval.pojo.lyricResults;
import com.example.infoRetrieval.service.RetrievalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RetrievalServiceImp implements RetrievalService {

    @Resource
    RawMapper rawMapper;

    @Override
    public List<Raw> getDocLocation(List<String> keywords) {
        List<Raw> results = new ArrayList<>();
        for (int i = 0; i < keywords.size(); i++) {
            List<Raw> temp = rawMapper.selectByPrimary(keywords.get(i));
            for (int j = 0; j < temp.size(); j++) {
                results.add(temp.get(j));
                System.out.println(temp.get(j).toString());
            }
        }

        return results;
    }
}
