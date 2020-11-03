package com.dbettkk.opg;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Test {
    /*
        E -> E '+' T | T
        T -> T '*' F | F
        F -> '(' E ')' | 'i'
     */
    static Map<Character, Integer> map = new HashMap<Character, Integer>();
    static int[][] opg = new int[6][6];
    static Stack<Character> stack = new Stack<Character>();

    public static void main(String[] args) throws IOException {
        init();
        String path = "D:\\大学\\大三上\\编译\\git仓库\\opg-compile\\test.txt";
        //String path = args[0];
        BufferedReader in = new BufferedReader(new FileReader(path));
        String str;
        while ((str = in.readLine()) != null) {
            //System.out.println(str);
            stack.clear();
            stack.push('#');
            //System.out.println("---------------");
            //System.out.println("分析字符串为：" + str);
            str = str + '#';
            checkOpg(str);
            //break;
        }
    }

    public static void checkOpg(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            judge(c);
            //stack.push(c);
            //System.out.println("I" + c);

        }
    }

    public static void judge(char c) {
        char now = stack.peek();
        if (now == 'V') {
            char t = stack.pop();
            now = stack.peek();
            stack.push(t);
        }
        if (c == '#' && now == '#') {
            return;
        }
        if (map.get(c) == null) {
            System.out.println("E");
            System.exit(0);
        }
        else {
            if (opg[map.get(now)][map.get(c)] == 1) {
                // 规约
                // 尝试规约
                StringBuilder tmp = new StringBuilder();
                boolean isCheck = false;
                while (true) {
                    char t = stack.pop();
                    if (t == '#') break;
                    tmp.append(t);
                    String res = check(tmp.toString());
                    if (!res.equals("")) {
                        if (res.equals("r")) {
                            stack.push('V');
                            System.out.println("R");
                        } else {
                            stack.push('V');
                            System.out.println("R");
                            for (int i=0;i<res.length();i++){
                                stack.push(res.charAt(i));
                            }
                        }
                        isCheck = true;
                        break;
                    }
                }
                if (isCheck) {
                    judge(c);
                } else {
                    System.out.println("RE");
                    System.exit(0);
                }

            } else if (opg[map.get(now)][map.get(c)] == -1) {
                // 移进
                stack.push(c);
                System.out.println("I" + c);
            } else if (opg[map.get(now)][map.get(c)] == 0) {
                // 移进
                stack.push(c);
                System.out.println("I" + c);
            } else if (opg[map.get(now)][map.get(c)] == 128) {
                // 错误
                System.out.println("E");
                System.exit(0);
            }
        }
    }

    public static String check(String str) {
        String[] legals = {
                "V+V", ")V(", "i", "V*V"
        };
        boolean isLegal = false;
        for (String legal : legals) {
            if (str.contains(legal)) {
                String tmp = str.substring(0, str.length() - legal.length());
                if (tmp.length() != 0) {
                    return tmp;
                }
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            return "";
        } else {
            return "r";
        }
    }

    public static void init() {
        map.put('+', 0);
        map.put('*', 1);
        map.put('i', 2);
        map.put('(', 3);
        map.put(')', 4);
        map.put('#', 5);
        opg[0] = new int[]{1, -1, -1, -1, 1, 1};
        opg[1] = new int[]{1, 1, -1, -1, 1, 1};
        opg[2] = new int[]{1, 1, 128, 128, 1, 1};
        opg[3] = new int[]{-1, -1, -1, -1, 0, 1};
        opg[4] = new int[]{1, 1, 128, 128, 1, 1};
        opg[5] = new int[]{-1, -1, -1, -1, -1, 0};
    }
}
