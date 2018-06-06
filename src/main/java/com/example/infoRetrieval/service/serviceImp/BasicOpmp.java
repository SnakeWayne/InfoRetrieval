package com.example.infoRetrieval.service.serviceImp;
import com.example.infoRetrieval.pojo.*;
import com.example.infoRetrieval.service.BasicOp;
import com.example.infoRetrieval.dao.*;
import com.example.infoRetrieval.service.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BasicOpmp implements BasicOp {
    @Resource
    private StemmerMapper stemmermapper;
    @Resource
    private StopTermMapper stoptermmapper;
    @Resource
    private CountdouMapper countdoumapper;
    @Resource
    private BeforeIndexMapper beforeindexmapper;
    @Resource
    private SmoothsingleMapper smoothsinglemapper;
    @Resource
    private SmoothdouMapper smoothdoumapper;
    @Resource
    private AMapper aMapper;
    @Resource
    private GoodexistdouMapper goodexistdouMapper;
    @Resource
    private GoodexistsingleMapper goodexistsingleMapper;
    HashMap<String,Double> a;
    HashMap<String,Double> goodexistsingle;
    HashMap<Pair,Double> goodexistdou;
    public BasicOpmp(){
        //一上来先进行初始化
       a=new HashMap<String,Double>();
        goodexistsingle=new HashMap<String,Double>();
        goodexistdou=new HashMap<Pair,Double>();
        AExample aExample=new AExample();
        List<A> alist=aMapper.selectByExample(aExample);
        for(int i=0;i<alist.size();i++){
            a.put(alist.get(i).getTerm(), Double.valueOf(alist.get(i).getA()));
        }
        GoodexistsingleExample goodexistsingleExample=new GoodexistsingleExample();
        List<Goodexistsingle> goodexistsingleList=goodexistsingleMapper.selectByExample(goodexistsingleExample);
        for(int i=0;i<goodexistsingleList.size();i++){
            goodexistsingle.put(goodexistsingleList.get(i).getTerm(),Double.valueOf(goodexistsingleList.get(i).getP()));
        }
        GoodexistdouExample goodexistdouExample=new GoodexistdouExample();
        List<Goodexistdou> goodexistdouList=goodexistdouMapper.selectByExample(goodexistdouExample);
        for(int i=0;i<goodexistdouList.size();i++){
            Pair pair=new Pairmp();
            pair.setWi1(goodexistdouList.get(i).getWi1());
            pair.setWi(goodexistdouList.get(i).getWi());
            goodexistdou.put(pair, Double.valueOf(goodexistdouList.get(i).getP()));
        }
    }


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

    @Override
    public HashMap<String, Double> rankPLM(String[] str) {
        HashMap<String, Double> result=new HashMap<String, Double>();
        BeforeIndexExample example = new BeforeIndexExample();
        List<BeforeIndex> doulist=beforeindexmapper.selectByExample(example);
        TreeSet<String> docnames=new TreeSet<String>();
        for(int i=0;i<doulist.size();i++){
                docnames.add(doulist.get(i).getDoc());
        }
        for (String doc : docnames) {
            result.put(doc,singleP(doc,str));
        }
        return result;
    }

    @Override
    public HashMap<String, Double> goodrankPLM(String[] str) {
        HashMap<String, Double> result=new HashMap<String, Double>();
        BeforeIndexExample example = new BeforeIndexExample();
        List<BeforeIndex> doulist=beforeindexmapper.selectByExample(example);
        TreeSet<String> docnames=new TreeSet<String>();
        for(int i=0;i<doulist.size();i++){
            docnames.add(doulist.get(i).getDoc());
        }
        for (String doc : docnames) {
            result.put(doc,goodsingleP(doc,str,a,goodexistsingle,goodexistdou));
        }
        return result;
    }

    @Override
    public Double goodsingleP(String doc, String[] query,HashMap<String,Double> a,HashMap<String,Double> goodexistsingle,HashMap<Pair,Double> goodexistdou) {
        for(int i=0;i<query.length;i++){
            query[i]=query[i].toUpperCase();
        }
        ArrayList<Pair> bigram=new ArrayList<Pair>();
        for(int i=1;i<query.length;i++){
            Pair temp=new Pairmp();
            temp.setWi1(query[i-1]);
            temp.setWi(query[i]);
            bigram.add(temp);
        }
        BeforeIndexExample singlexam=new BeforeIndexExample();
        BeforeIndexExample.Criteria criteria1=singlexam.createCriteria();
        criteria1.andDocEqualTo(doc);
        List<BeforeIndex> singlelist=beforeindexmapper.selectByExample(singlexam);

        CountdouExample   douexam=new CountdouExample();
        CountdouExample.Criteria criteria=douexam.createCriteria();
        criteria.andDocEqualTo(doc);
        List<Countdou> doulist=countdoumapper.selectByExample(douexam);
        Double score=1.0;
        for(int i=0;i<bigram.size();i++){
            if(i==0){
                continue;
                // score=score+Math.log(getsinglec(bigram.get(i).getWi1(),singlelist));
            }
            else{
                double up=getdoublec(bigram.get(i),doulist);
                double down=getsinglec(bigram.get(i).getWi1(),singlelist);
                if(up==0||down==0){
                    score=score*collectiondouP(a,goodexistsingle,goodexistdou,bigram.get(i));
                    continue;
                }
                else {
                    score = score *up / down;
                }
            }
        }
        return score;
    }
    public double collectiondouP(HashMap<String,Double> a,HashMap<String,Double> goodexistsingle,HashMap<Pair,Double> goodexistdou,Pair pair){
        double Pdou=0;
        double Psingle=0;
        Pdou=goodexistdou.get(pair);
        Psingle=goodexistsingle.get(pair.getWi1());
        if(Pdou!=0)
            return Pdou;

        if(Pdou==0&&Psingle!=0)
                return a.get(pair.getWi1())*collectionsingleP(goodexistsingle,pair.getWi());

        if(Pdou==0&&Psingle==0)
            return collectionsingleP(goodexistsingle,pair.getWi());


        return 0.0;



    }
    public double collectionsingleP(HashMap<String,Double> goodexistsingle,String term){
        if(goodexistsingle.get(term)!=null)
            return goodexistsingle.get(term);
        else
            //为出现一次的单词个数，56082为总歌词次数，0.001为估计未包含的单词个数
            return 894/56082*0.002;
    }
    @Override
    public Double singleP(String doc,String[] query) {
        for(int i=0;i<query.length;i++){
            query[i]=query[i].toUpperCase();
        }
        ArrayList<Pair> bigram=new ArrayList<Pair>();
        for(int i=1;i<query.length;i++){
            Pair temp=new Pairmp();
            temp.setWi1(query[i-1]);
            temp.setWi(query[i]);
            bigram.add(temp);
        }

//        CountdouExample example = new CountdouExample();
//        CountdouExample.Criteria criteria = example.createCriteria();
//        criteria.andDocEqualTo(doc);
//        example.setOrderByClause("wi1 asc,wi asc");
//        List<Countdou> countlist = countdoumapper.selectByExample(example);
//        BeforeIndexExample singleexample = new BeforeIndexExample();
//        BeforeIndexExample.Criteria singlecriteria = singleexample.createCriteria();
//        singlecriteria.andDocEqualTo(doc);

//        List<BeforeIndex> singlelist = beforeindexmapper.selectByExample(singleexample);
//        ArrayList<Integer>  Nsingle=new ArrayList<Integer>();
//        ArrayList<Integer>  N=new ArrayList<Integer>();
//此版本为失败的平滑处理版本，出现的数值很怪异
//        SmoothdouExample douexam=new SmoothdouExample();
//        SmoothdouExample.Criteria criteria=douexam.createCriteria();
//        criteria.andDocEqualTo(doc);
//        List<Smoothdou> doulist=smoothdoumapper.selectByExample(douexam);
//
//        SmoothsingleExample singlexam=new SmoothsingleExample();
//        SmoothsingleExample.Criteria criteria1=singlexam.createCriteria();
//        criteria1.andDocEqualTo(doc);
//        List<Smoothsingle> singlelist=smoothsinglemapper.selectByExample(singlexam);
        BeforeIndexExample singlexam=new BeforeIndexExample();
        BeforeIndexExample.Criteria criteria1=singlexam.createCriteria();
        criteria1.andDocEqualTo(doc);
        List<BeforeIndex> singlelist=beforeindexmapper.selectByExample(singlexam);

        CountdouExample   douexam=new CountdouExample();
        CountdouExample.Criteria criteria=douexam.createCriteria();
        criteria.andDocEqualTo(doc);
        List<Countdou> doulist=countdoumapper.selectByExample(douexam);
        Double score=1.0;
        for(int i=0;i<bigram.size();i++){
                if(i==0){
                    continue;
                   // score=score+Math.log(getsinglec(bigram.get(i).getWi1(),singlelist));
                }
                else{
                    double up=getdoublec(bigram.get(i),doulist);
                    double down=getsinglec(bigram.get(i).getWi1(),singlelist);
                    if(up==0||down==0){
                        score=score*0;
                        continue;
                    }
                    else {
                        score = score *up / down;
                    }
                }
        }


//        for(int i=0;i<=query.length;i++){
//            N.add(0);
//        }
//        Comparator c = new Comparator<Pair>() {
//            @Override
//            public int compare(Pair o1, Pair o2) {
//                if(o1.getWi1().equals(o2.getWi1()))
//                return o1.getWi().compareTo(o2.getWi());
//                else
//                    return o1.getWi1().compareTo(o2.getWi1());
//            }
//        };
//        bigram.sort(c);
//
//        for(int i=0,j=0;i<bigram.size()&&j<countlist.size();){
//                if((bigram.get(i).getWi1().equals(countlist.get(j).getWi1()))&&(bigram.get(i).getWi().equals(countlist.get(j).getWi()))){
//                    int num=countlist.get(j).getFreq();
//                    N.set(num,N.get(num)+1);
//                    i++;
//                    j++;
//                }
//                else{
//                    if((bigram.get(i).getWi1().compareTo(countlist.get(j).getWi1()))>0){
//                        j++;
//                    }
//                    if((bigram.get(i).getWi1().compareTo(countlist.get(j).getWi1()))<0){
//                        N.set(0,N.get(0)+1);
//                        i++;
//                    }
//                    if((bigram.get(i).getWi1().compareTo(countlist.get(j).getWi1()))==0){
//                        if((bigram.get(i).getWi1().compareTo(countlist.get(j).getWi1()))>0){
//                            j++;
//                        }
//                        else{
//                            N.set(0,N.get(0)+1);
//                            i++;
//                        }
//                    }
//                }
//        }


        return score;
    }

    @Override
    public double getsinglec(String str, List<BeforeIndex> list) {
        double nul=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getTerm().equals(str)){
                return list.get(i).getFreq();
            }
            if(list.get(i).getTerm().equals("null")){
                nul=list.get(i).getFreq();
            }

        }

        return nul;
    }

    @Override
    public double getdoublec(Pair pair, List<Countdou> list) {
        double nul=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getWi1().equals(pair.getWi1())&&list.get(i).getWi().equals(pair.getWi())){
                return list.get(i).getFreq();
            }
            if(list.get(i).getWi1().equals("null")&&list.get(i).getWi().equals("null")){
                nul=list.get(i).getFreq();
            }

        }

        return nul;
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
