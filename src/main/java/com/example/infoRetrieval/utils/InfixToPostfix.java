package com.example.infoRetrieval.utils;

import antlr.collections.Stack;

import java.util.*;

public class InfixToPostfix {
    private static Map<String, Integer> priority = new HashMap<>();
    static {
        priority.put("&", 1);
        priority.put("|", 1);
        priority.put("~", 2);
    }

    public static String[] convertTo(String param) {
        String []infixStr = param.split(" ");
        String []postfixStr = new String[infixStr.length];
        Stack pStack = new Stack() {

            List<String> st = new ArrayList<>();
            int point = -1;

            @Override
            public int height() {
                return st.size();
            }

            @Override
            public Object pop() throws NoSuchElementException {
                if (point != -1) {
                    String temp = st.get(point);
                    st.remove(point);
                    point --;
                    return temp;
                }
                return null;
            }

            @Override
            public void push(Object o) {
                point ++;
                st.add((String) o);
            }

            @Override
            public Object top() throws NoSuchElementException {
                if (point != -1)
                    return st.get(point);
                else
                    return null;
            }
        };
        int k = 0;

        for (int i = 0; i < infixStr.length; i ++ ) {
            String temp = infixStr[i];
            if (! temp.equals("|") && ! temp.equals("&") && ! temp.equals("~") && ! temp.equals(")") && ! temp.equals("(")) {
                postfixStr[k] = temp;
                k ++;
            } else if (temp.equals(")")) {
                while ( ! pStack.top().equals("(") && pStack.top() != null) {
                    postfixStr[k] = (String) pStack.pop();
                    k ++;
                }
                pStack.pop();
            } else if (temp.equals("(")) {
                pStack.push(temp);
            } else {
                String top = (String) pStack.top();
                while (compareOperator(temp, top) && pStack.height() != 0) {
                    postfixStr[k] = (String) pStack.pop();
                    k ++;
                    top = (String) pStack.top();
                }
                pStack.push(temp);
            }
        }
        while(pStack.top() != null) {
            String tempp = (String) pStack.pop();
            postfixStr[k] = tempp;
            k ++;
        }
        ArrayList<String> re = new ArrayList<>();
        for (int i = 0; i < postfixStr.length; i ++) {
            String temp = postfixStr[i];
            if (temp !=null)
                re.add(temp);
        }
        String[] ress = new String[re.size()];
        for (int i = 0; i < ress.length; i ++) {
            ress[i] = re.get(i);
        }
        return ress;
    }

    //是否出栈
    private static boolean compareOperator (String cur, String st) {
        if (st == null)
            return false;
        if (st.equals("("))
            return false;
        return priority.get(cur) <= priority.get(st);
    }

//    public static void main(String[] args) {
//        String a = "~ ( abc & ( dge | ~ ppp ) & dge )";
//        String[] re = convertTo(a);
//        for (int i = 0; i < re.length; i++)
//            System.out.println(re[i]);
//    }
}
