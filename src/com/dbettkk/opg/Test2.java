package com.dbettkk.opg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Test2 {
    /*
        E -> E '+' T | T
        T -> T '*' F | F
        F -> '(' E ')' | 'i'
     */
    static Map<Character, Integer> map = new HashMap<Character, Integer>();
    static int[][] opg = new int[6][6];
    static Stack<Character> opStack = new Stack<Character>();
    static Stack<Character> objStack = new Stack<Character>();

    public static void main(String[] args) throws IOException {
        init();
        //String path = "D:\\大学\\大三上\\编译\\git仓库\\opg-compile\\test.txt";
        String path = args[0];
        BufferedReader in = new BufferedReader(new FileReader(path));
        String str;
        while ((str = in.readLine()) != null) {
            //System.out.println(str);
            opStack.clear();
            objStack.clear();
            opStack.push('#');
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
        if (map.get(c) == null) {
            System.out.println("E");
            return;
        }
        if (c == 'i') {
            objStack.push(c);
            System.out.println("I" + c);
        } else if (opg[map.get(opStack.peek())][map.get(c)] == 1) {
            // 规约
            try {
                objStack.pop();
                opStack.pop();
                System.out.println('R');
                objStack.pop();
                objStack.push('i');
                judge(c);
            } catch (Exception e) {
                System.out.println("RE");
                System.exit(0);
            }
        } else if (opg[map.get(opStack.peek())][map.get(c)] == 0) {
            if (c == ')') {
                System.out.println("I)");
            }
            opStack.pop();
            System.out.println('R');
        } else if (opg[map.get(opStack.peek())][map.get(c)] < 0) {
            opStack.push(c);
            System.out.println("R");
            System.out.println("I" + c);
        } else if (opg[map.get(opStack.peek())][map.get(c)] == 128) {
            // 错误
            System.out.println("RE");
        } else if (opStack.peek() == c && c == '#') {
            // 结束
            return;
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
