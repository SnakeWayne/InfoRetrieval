package com.example.infoRetrieval.utils;


import com.example.infoRetrieval.pojo.lyricResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BplusTree <K extends Comparable<K>, V> {

    /**
     * 根节点
     */
    protected BplusNode<K, V> root;

    /**
     * 阶数，M值
     */
    protected int order;

    /**
     * 叶子节点的链表头
     */
    protected BplusNode<K, V> head;

    /**
     * 树高
     */
    protected int height = 0;

    public BplusNode<K, V> getHead() {
        return head;
    }

    public void setHead(BplusNode<K, V> head) {
        this.head = head;
    }

    public BplusNode<K, V> getRoot() {
        return root;
    }

    public void setRoot(BplusNode<K, V> root) {
        this.root = root;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public V get(K key) {
        return root.get(key);
    }

    public BplusNode<K, V> getNode(K key) {
        return root.getNode(key);
    }

    public V remove(K key) {
        return root.remove(key, this);
    }

    public void insertOrUpdate(K key, V value) {
        root.insertOrUpdate(key, value, this);

    }

    public BplusTree(int order) {
        if (order < 3) {
            System.out.print("order must be greater than 2");
            System.exit(0);
        }
        this.order = order;
        root = new BplusNode<K, V>(true, true);
        head = root;
    }

    public List<lyricResults> linerSearch(String from, String to) {
        List<lyricResults> results = new ArrayList<>();
        BplusNode<K, V> node = getNode((K) from);

        while (true) {
            List<Map.Entry<K, V>> temp = node.getEntries();
            boolean flag = false;
            for (int i = 0; i < temp.size(); i++) {
                if (from.compareTo((String)temp.get(i).getKey()) <= 0 && to.compareTo((String) temp.get(i).getKey()) > 0) {
                    String a = (String) temp.get(i).getKey();
                    String b = (String) temp.get(i).getValue();
                    lyricResults r = new lyricResults();
                    r.setWord(a);
                    r.setFileName(b);
                    results.add(r);
                }
                if (to.compareTo((String) temp.get(i).getKey()) <= 0) {
                    flag = true;
                    break;
                }
            }
            if (flag)
                break;
            else {
                node = node.getNextNode();
            }
        }
        return results;
    }
    public List<lyricResults> linerSearchEnd(String from, String to) {
        List<lyricResults> results = new ArrayList<>();
        BplusNode<K, V> node = getNode((K) from);
        char tt = from.charAt(from.length() - 1);

        while (true) {
            List<Map.Entry<K, V>> temp = node.getEntries();
            boolean flag = false;
            for (int i = 0; i < temp.size(); i++) {
                if (from.compareTo((String)temp.get(i).getKey()) <= 0 && to.compareTo((String) temp.get(i).getKey()) > 0) {
                    String a = (String) temp.get(i).getKey();
                    String b = (String) temp.get(i).getValue();
                    if (a.charAt(a.length() - 1) == '$') {
                        lyricResults r = new lyricResults();
                        r.setWord(a);
                        r.setFileName(b);
                        results.add(r);
                    }
                }
                if (to.compareTo((String) temp.get(i).getKey()) <= 0) {
                    flag = true;
                    break;
                }
            }
            if (flag)
                break;
            else {
                node = node.getNextNode();
            }
        }
        return results;
    }

//    public static void main(String[] args) {
//
//        int size = 100;
//        int order = 3;
//       testOrderSearch(size, order);
//    }
//
//    private static void testOrderSearch(int size, int order) {
//        BplusTree<Integer, Integer> tree = new BplusTree<Integer, Integer>(order);
//        System.out.println("\nTest order search " + size + " datas, of order:"
//                + order);
//        System.out.println("Begin order insert...");
//        for (int i = 0; i < size; i++) {
//            tree.insertOrUpdate(i, i);
//        }
//        System.out.println("Begin order search...");
//        long current = System.currentTimeMillis();
//        for (int j = 0; j < size; j++) {
//            if (tree.get(j) == null) {
//                System.err.println("得不到数据:" + j);
//                break;
//            }
//        }
//        long duration = System.currentTimeMillis() - current;
//        System.out.println("time elpsed for duration: " + duration);
//    }

}