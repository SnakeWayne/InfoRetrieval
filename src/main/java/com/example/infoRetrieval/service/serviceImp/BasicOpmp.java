package com.example.infoRetrieval.service.serviceImp;
import com.example.infoRetrieval.pojo.BeforeIndex;
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
    private StemmerMapper stemmermapper;
    @Resource
    private StopTermMapper stoptermmapper;



    @Override
    public HashMap<String, ArrayList<String>> andOp(String stra, ArrayList<String> lista, String strb, ArrayList<String> listb) {
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

        String resultstr=stra+strb+"~&";
        HashMap<String,ArrayList<String>> reshash=new HashMap<String,ArrayList<String>>();
        reshash.put(resultstr,resultlist);
        return reshash;
    }

    @Override
    public ArrayList<lyricResults> multiAnd(String[] str) {
        ArrayList<StopTerm> stops=stoptermmapper.selectAll();
        ArrayList<String> stopterms=new ArrayList<String>();

        for(int i=0;i<stops.size();i++){
            stopterms.add(stops.get(i).getTerm());
        }
        ArrayList<lyricResults> result=new ArrayList<lyricResults>();
       HashMap<String,ArrayList<String>> all=new HashMap<String,ArrayList<String>>();
        for(String term:str){

            if(term.equals("&")){
                continue;
            }
            ArrayList<String> temp=new ArrayList<String>();
            if(stopterms.contains(term)){
                continue;
            }
            ArrayList<BeforeIndex> list=stemmermapper.blurSelect(term.toUpperCase());
            for(int i=0;i<list.size();i++){
                     temp.add(list.get(i).getDoc());
            }
            all.put(term,temp);
        }

        List<Map.Entry<String, ArrayList<String>>> list = new ArrayList<Map.Entry<String, ArrayList<String>>>(all.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, ArrayList<String>>>() {
            @Override
            public int compare(Map.Entry<String, ArrayList<String>> o1, Map.Entry<String, ArrayList<String>> o2) {
                return o1.getValue().size()-o2.getValue().size(); // 升序
            }
        });
        //完成排序工作 一下开始and操作


        //初始加入两个
        String a=new String();
        String b=new String();
        ArrayList<String> lista=new ArrayList<String>();
        ArrayList<String> listb=new ArrayList<String>();
        int inicounter=0;
        for (Map.Entry<String, ArrayList<String>> entry : all.entrySet()) {
            if(inicounter==0) {
                a = entry.getKey();
                lista = entry.getValue();
                inicounter++;
            }
            else{
                b = entry.getKey();
                listb = entry.getValue();
                Map.Entry<String, ArrayList<String>> tempentry=andOp(a,lista,b,listb).entrySet().iterator().next();
                for(int i=0;i<tempentry.getValue().size();i++){
                    result.add(new lyricResults(tempentry.getKey(),tempentry.getValue().get(i)));
                }
                a=tempentry.getKey();
                lista=tempentry.getValue();
                inicounter++;
            }
        }
        return result;
    }

    @Override
    public ArrayList<lyricResults> multiOr(String[] str) {
        ArrayList<StopTerm> stops=stoptermmapper.selectAll();
        ArrayList<String> stopterms=new ArrayList<String>();

        for(int i=0;i<stops.size();i++){
            stopterms.add(stops.get(i).getTerm());
        }
        ArrayList<lyricResults> result=new ArrayList<lyricResults>();
        HashMap<String,ArrayList<String>> all=new HashMap<String,ArrayList<String>>();
        for(String term:str){
            //if(term.equals("&")||term.equals("|")||term.equals("~"))
            if(term.equals("|")){
                continue;
            }
            if(stopterms.contains(term)){
                continue;
            }
            ArrayList<String> temp=new ArrayList<String>();

            ArrayList<BeforeIndex> list=stemmermapper.blurSelect(term.toUpperCase());
            for(int i=0;i<list.size();i++){
                temp.add(list.get(i).getDoc());
            }
            all.put(term,temp);
        }

        List<Map.Entry<String, ArrayList<String>>> list = new ArrayList<Map.Entry<String, ArrayList<String>>>(all.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, ArrayList<String>>>() {
            @Override
            public int compare(Map.Entry<String, ArrayList<String>> o1, Map.Entry<String, ArrayList<String>> o2) {
                return o1.getValue().size()-o2.getValue().size(); // 升序
            }
        });
        //完成排序工作 一下开始or操作


        //初始加入两个
        String a=new String();
        String b=new String();
        ArrayList<String> lista=new ArrayList<String>();
        ArrayList<String> listb=new ArrayList<String>();
        int inicounter=0;
        for (Map.Entry<String, ArrayList<String>> entry : all.entrySet()) {
            if(inicounter==0) {
                a = entry.getKey();
                lista = entry.getValue();
                inicounter++;
            }
            else{
                b = entry.getKey();
                listb = entry.getValue();
                Map.Entry<String, ArrayList<String>> tempentry=orOp(a,lista,b,listb).entrySet().iterator().next();
                for(int i=0;i<tempentry.getValue().size();i++){
                    result.add(new lyricResults(tempentry.getKey(),tempentry.getValue().get(i)));
                }
                a=tempentry.getKey();
                lista=tempentry.getValue();
                inicounter++;
            }
        }
        return result;
    }

    @Override
    public ArrayList<lyricResults> multiNotAnd(String[] str) {

        ArrayList<StopTerm> stops=stoptermmapper.selectAll();
        ArrayList<String> stopterms=new ArrayList<String>();

        for(int i=0;i<stops.size();i++){
            stopterms.add(stops.get(i).getTerm());
        }
        ArrayList<lyricResults> result=new ArrayList<lyricResults>();
        HashMap<String,ArrayList<String>> all=new HashMap<String,ArrayList<String>>();
        for(String term:str){
            //if(term.equals("&")||term.equals("|")||term.equals("~"))
            if(term.equals("~")){
                continue;
            }
            if(stopterms.contains(term)){
                continue;
            }
            ArrayList<String> temp=new ArrayList<String>();

            ArrayList<BeforeIndex> list=stemmermapper.blurSelect(term.toUpperCase());
            for(int i=0;i<list.size();i++){
                temp.add(list.get(i).getDoc());
            }
            all.put(term,temp);
        }

        List<Map.Entry<String, ArrayList<String>>> list = new ArrayList<Map.Entry<String, ArrayList<String>>>(all.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, ArrayList<String>>>() {
            @Override
            public int compare(Map.Entry<String, ArrayList<String>> o1, Map.Entry<String, ArrayList<String>> o2) {
                return o1.getValue().size()-o2.getValue().size(); // 升序
            }
        });
        //完成排序工作 一下开始and操作


        //初始加入两个
        String a=new String();
        String b=new String();
        ArrayList<String> lista=new ArrayList<String>();
        ArrayList<String> listb=new ArrayList<String>();
        int inicounter=0;
        for (Map.Entry<String, ArrayList<String>> entry : all.entrySet()) {
            if(inicounter==0) {
                a = entry.getKey();
                lista = entry.getValue();
                inicounter++;
            }
            else{
                b = entry.getKey();
                listb = entry.getValue();
                Map.Entry<String, ArrayList<String>> tempentry=notAndOp(a,lista,b,listb).entrySet().iterator().next();
                for(int i=0;i<tempentry.getValue().size();i++){
                    result.add(new lyricResults(tempentry.getKey(),tempentry.getValue().get(i)));
                }
                a=tempentry.getKey();
                lista=tempentry.getValue();
                inicounter++;
            }
        }
        return result;
    }

    @Override
    public ArrayList<lyricResults> multiOp(String[] str) {
        Stemmer stemmer=new Stemmer();
        ArrayList<StopTerm> stops=stoptermmapper.selectAll();
        ArrayList<String> stopterms=new ArrayList<String>();
        //停词表需要处理

        for(int i=0;i<stops.size();i++){
            stopterms.add(stops.get(i).getTerm());
        }
        HashMap<String,ArrayList<String>> all=new HashMap<String,ArrayList<String>>();
        boolean hasand=false;
        boolean hasor=false;
        boolean hasnotand=false;
        for(String term:str){
            if(term.equals("&")){
                hasand=true;
            }
            if(term.equals("|")){
                hasor=true;
            }
            if(term.equals("~")){
                hasnotand=true;
            }

        }
        if(hasand&&!hasor&&!hasnotand){
            return multiAnd(str);
        }
        if(!hasand&&hasor&&!hasnotand){
            return multiOr(str);
        }
        if(!hasand&&!hasor&&hasnotand){
            return multiNotAnd(str);
        }
        Stack<String> strStack=new Stack<String>();

        ArrayList<lyricResults> result=new ArrayList<lyricResults>();
        String a=new String();
        String b=new String();
        ArrayList<String> lista=new ArrayList<String>();
        ArrayList<String> listb=new ArrayList<String>();
        ArrayList<BeforeIndex> list_a=new ArrayList<BeforeIndex>();
        ArrayList<BeforeIndex> list_b=new ArrayList<BeforeIndex>();
        int inicounter=0;
       // Map.Entry<String, ArrayList<String>> tempentry=andOp(a,lista,b,listb).entrySet().iterator().next();
        for(String term:str){
            if(term.equals("&")||term.equals("|")){
                boolean containnot=false;
                a=strStack.pop();
               if(a.equals("~")){
                   containnot=true;
                   //~ab
                   b=strStack.pop();
                   a=strStack.pop();
               }
               else{
                   b=strStack.pop();
                   if(b.equals("~")){
                       //a~b
                       containnot=true;
                       b=strStack.pop();
                   }

               }


               //成功取到ab，接下来判断是否需要查数据库
                if(stopterms.contains(a.toUpperCase())){
                   lista=new ArrayList<String>();
                }
                else {
                    if (all.containsKey(a)) {
                        lista = all.get(a);
                    } else {
                        list_a = stemmermapper.blurSelect(stemmer.getResult(a));
                        for (int i = 0; i < list_a.size(); i++) {
                            lista.add(list_a.get(i).getDoc());
                        }
                    }
                }
                if(stopterms.contains(b.toUpperCase())){
                    listb=new ArrayList<String>();
                }
                else {
                    if (all.containsKey(b)) {
                        listb = all.get(b);
                    } else {
                        list_b = stemmermapper.blurSelect(stemmer.getResult(b));
                        for (int i = 0; i < list_b.size(); i++) {
                            System.out.println(i);
                            BeforeIndex temp = list_b.get(i);
                            String temp2 = temp.getDoc();
                            listb.add(temp2);
                        }
                    }
                }


               //接下来判断是采用哪种操作;
                if(containnot){
                    Map.Entry<String, ArrayList<String>> tempentry = notAndOp(a, lista, b, listb).entrySet().iterator().next();
                    for (int i = 0; i < tempentry.getValue().size(); i++) {
                        result.add(new lyricResults(tempentry.getKey(), tempentry.getValue().get(i)));
                    }
                    all.put(tempentry.getKey(),tempentry.getValue());
                    strStack.push(tempentry.getKey());
                }
                else {
                    if (term.equals("&")) {
                        Map.Entry<String, ArrayList<String>> tempentry = andOp(a, lista, b, listb).entrySet().iterator().next();
                        for (int i = 0; i < tempentry.getValue().size(); i++) {
                            result.add(new lyricResults(tempentry.getKey(), tempentry.getValue().get(i)));
                        }
                        all.put(tempentry.getKey(),tempentry.getValue());
                        strStack.push(tempentry.getKey());
                    }
                    else{
                        Map.Entry<String, ArrayList<String>> tempentry = orOp(a, lista, b, listb).entrySet().iterator().next();
                        for (int i = 0; i < tempentry.getValue().size(); i++) {
                            result.add(new lyricResults(tempentry.getKey(), tempentry.getValue().get(i)));
                        }
                        all.put(tempentry.getKey(),tempentry.getValue());
                        strStack.push(tempentry.getKey());
                    }
                }
            }
            else{
                strStack.push(term);
            }
        }
        return result;
    }

//    public static void main(String[] args){
//        BasicOpmp temp=new BasicOpmp();
//        ArrayList<String> a=new ArrayList<String>();
//        ArrayList<String> b=new ArrayList<String>();
//        a.add("1.1");
//        a.add("5.1");
//
//        b.add("5.1");
//       ;
//        System.out.println(temp.orOp("a",a,"b",b).get("ab|"));
//        HashMap<String,ArrayList<String>> all=new HashMap<String,ArrayList<String>>();
//        all.put("a",a);
//        all.put("b",b);
//        List<Map.Entry<String, ArrayList<String>>> list = new ArrayList<Map.Entry<String, ArrayList<String>>>(all.entrySet());
//
//
//// 然后通过比较器来实现排序
//        Collections.sort(list, new Comparator<Map.Entry<String, ArrayList<String>>>() {
//            @Override
//            public int compare(Map.Entry<String, ArrayList<String>> o1, Map.Entry<String, ArrayList<String>> o2) {
//                // return 0;  // 降序
//                // return o2.getValue().compareTo(o1.getValue()); // 降序
//                return o1.getValue().size()-o2.getValue().size(); // 升序
//            }
//        });
//
//        for (Map.Entry<String, ArrayList<String>> mapping : list) {
//            System.out.println(mapping.getKey() + ":" + mapping.getValue());
//        }
//    }
}
