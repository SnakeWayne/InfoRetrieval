package com.example.infoRetrieval.service.serviceImp;

import com.example.infoRetrieval.dao.AfterIndexMapper;
import com.example.infoRetrieval.dao.PermIndexMapper;
import com.example.infoRetrieval.dao.RawMapper;
import com.example.infoRetrieval.pojo.AfterIndex;
import com.example.infoRetrieval.pojo.PermIndex;
import com.example.infoRetrieval.pojo.Raw;
import com.example.infoRetrieval.pojo.lyricResults;
import com.example.infoRetrieval.service.StartOrEndWildCardQuery;
import com.example.infoRetrieval.utils.BplusTree;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StartOrEndWildCardQueryImp implements StartOrEndWildCardQuery {
    private BplusTree<String, String> forEnd;
    @Resource
    private PermIndexMapper permIndexMapper;
    @Resource
    private RawMapper rawMapper;
    @Override
    public List<lyricResults> query(String key) {
        List<lyricResults> results = new ArrayList();
        if (forEnd == null) {init();}
        String lowStr = key;
        char[] hchars = key.toCharArray();
        char high = hchars[key.length() - 1];
        hchars[key.length() - 1] = (char) ((char) high + 1);
        String highStr = new String(hchars);
        results = forEnd.linerSearch(lowStr, highStr);
        return results;
    }

    public List<lyricResults> queryEnd(String key) {
        List<lyricResults> results = new ArrayList();
        if (forEnd == null) {init();}
        String lowStr = key;
        char[] hchars = key.toCharArray();
        char high = hchars[key.length() - 1];
        hchars[key.length() - 1] = (char) ((char) high + 1);
        String highStr = new String(hchars);
        results = forEnd.linerSearchEnd(lowStr, highStr);
        return results;
    }
    private void init() {
        forEnd = new BplusTree<String, String>(10);
        List<PermIndex> temp = permIndexMapper.getAll();
        for (int i = 0; i < temp.size(); i++) {
            forEnd.insertOrUpdate(temp.get(i).getVaria(), temp.get(i).getProto());
        }
    }
}
