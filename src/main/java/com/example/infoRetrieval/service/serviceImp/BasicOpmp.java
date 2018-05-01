package com.example.infoRetrieval.service.serviceImp;

import com.example.infoRetrieval.service.BasicOp;
import org.hibernate.mapping.Collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class BasicOpmp implements BasicOp {

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

                    resultlist.add(lista.get(j));
                    j++;
                } else {

                    resultlist.add(lista.get(i));
                    i++;
                }
            }

        }
        String resultstr=stra+strb+"|";
        HashMap<String,ArrayList<String>> reshash=new HashMap<String,ArrayList<String>>();
        reshash.put(resultstr,resultlist);
        return reshash;

    }

    @Override
    public HashMap<String, ArrayList<String>> notAndOp(String stra, ArrayList<String> lista, String strb, ArrayList<String> listb) {
        return null;
    }

    @Override
    public HashMap<String, ArrayList<String>> multiOp(String[] str) {
        return null;
    }
    public static void main(String[] args){
        BasicOpmp temp=new BasicOpmp();
        ArrayList<String> a=new ArrayList<String>();
        ArrayList<String> b=new ArrayList<String>();
        a.add("a");
        a.add("b");
        a.add("c");
        b.add("a");
        b.add("b");
        b.add("c");
        System.out.println(temp.addOp("a",a,"b",b).get("ab&"));


    }
}
