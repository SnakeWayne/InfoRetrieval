package com.example.infoRetrieval.service.serviceImp;
import com.example.infoRetrieval.pojo.StopTerm;
import com.example.infoRetrieval.pojo.lyricResults;
import com.example.infoRetrieval.service.BasicOp;
import com.example.infoRetrieval.dao.*;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
@Service
public class BasicOpmp implements BasicOp {
    @Resource
   private StopTermMapper stopp;
     public List<StopTerm> getStudentsById(int StudentsId) {
        return stopp.selectLike("asd");
    }
    @Override
    public HashMap<String, ArrayList<String>> addOp(String stra, ArrayList<String> lista, String strb, ArrayList<String> listb) {
        ArrayList<String> resultlist=new ArrayList<String>();
        Collections.sort(lista);
        Collections.sort(listb);
        int i=0,j=0;
        for(;i<lista.size()&&j<listb.size();){
            if(lista.get(i).equals(listb.get(j))){
                resultlist.add(lista.get(i));
                i++;
                j++;

            }
            else{
                 if(lista.get(i).compareTo(listb.get(j))>0)
                     j++;
                 else
                     i++;
            }

        }
        String resultstr=stra+strb+"&";
        HashMap<String,ArrayList<String>> reshash=new HashMap<String,ArrayList<String>>();
        reshash.put(resultstr,resultlist);
        return reshash;
    }

    @Override
    public HashMap<String, ArrayList<String>> orOp(String stra, ArrayList<String> lista, String strb, ArrayList<String> listb) {
        ArrayList<String> resultlist=new ArrayList<String>();
        Collections.sort(lista);
        Collections.sort(listb);
        int i=0,j=0;
        for(;i<lista.size()&&j<listb.size();){
            if(lista.get(i).equals(listb.get(j))){
                resultlist.add(lista.get(i));
                i++;
                j++;

            }
            else {
                if (lista.get(i).compareTo(listb.get(j)) > 0) {

                    resultlist.add(listb.get(j));
                    j++;
                } else {

                    resultlist.add(lista.get(i));
                    i++;
                }
            }

        }
        if(i<lista.size()){
            for(;i<lista.size();i++){
                resultlist.add(lista.get(i));
            }
        }

        if(j<listb.size()){
            for(;j<listb.size();j++){
                resultlist.add(listb.get(j));
            }
        }

        String resultstr=stra+strb+"|";
        HashMap<String,ArrayList<String>> reshash=new HashMap<String,ArrayList<String>>();
        reshash.put(resultstr,resultlist);
        return reshash;

    }

    @Override
    public HashMap<String, ArrayList<String>> notAndOp(String stra, ArrayList<String> lista, String strb, ArrayList<String> listb) {
        ArrayList<String> resultlist=new ArrayList<String>();
        Collections.sort(lista);
        Collections.sort(listb);
        int i=0,j=0;
        for(;i<lista.size()&&j<listb.size();){
            if(lista.get(i).equals(listb.get(j))){
                i++;
                j++;

            }
            else {
                if (lista.get(i).compareTo(listb.get(j)) > 0) {

                    resultlist.add(listb.get(j));
                    j++;
                } else {

                    resultlist.add(lista.get(i));
                    i++;
                }
            }

        }
        if(i<lista.size()){
            for(;i<lista.size();i++){
                resultlist.add(lista.get(i));
            }
        }

        if(j<listb.size()){
            for(;j<listb.size();j++){
                resultlist.add(listb.get(j));
            }
        }
//        List<StopTerm> list=stopp.selectLike("A");
//        for(StopTerm ss:list){
//                 System.out.println(ss.getTerm());
//        }

        String resultstr=stra+strb+"~&";
        HashMap<String,ArrayList<String>> reshash=new HashMap<String,ArrayList<String>>();
        reshash.put(resultstr,resultlist);
        return reshash;
    }

    @Override
    public ArrayList<lyricResults> multiOp(String[] str) {
        Stack<String> strStack=new Stack<String>();

        return null;
    }
    public static void main(String[] args){
        BasicOpmp temp=new BasicOpmp();
        ArrayList<String> a=new ArrayList<String>();
        ArrayList<String> b=new ArrayList<String>();
        a.add("a");
        a.add("b");
        a.add("d");
        b.add("a");
        b.add("b");
        b.add("d");
        b.add("asd");
        System.out.println(temp.notAndOp("a",a,"b",b).get("ab~&"));


    }
}
