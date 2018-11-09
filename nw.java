package com.summer.sw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class nw {
    static StringBuffer s = new StringBuffer(); // s串
    static StringBuffer t = new StringBuffer(); // t串
    static StringBuffer ds = new StringBuffer(); // s串
    static StringBuffer dt = new StringBuffer(); // t串

    public static void main(String[] args) {
        // 子串读入
        InputStream fs = null;
        try {
            fs = new FileInputStream("/Users/summerchaser/Desktop/ds.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStream ft = null;
        try {
            ft = new FileInputStream("/Users/summerchaser/Desktop/dt.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int c;
        try {
            while ((c = fs.read()) != -1) {
                Character m = new Character((char) c);
                if (Character.isLetter(m)) {
                    s.append(m.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while ((c = ft.read()) != -1) {
                t.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("s串为： " + s);
        System.out.println("t串为： " + t);

        int m = s.length() + 1;
        int n = t.length() + 1;
        final int[][] BLOSUM62 = { { 4, -1, -2, -2, 0, -1, -1, 0, -2, -1, -1, -1, -1, -2, -1, 1, 0, -3, -2, 0 },
                { -1, 5, 0, -2, -3, 1, 0, -2, 0, -3, -2, 2, -1, -3, -2, -2, -1, -3, -2, -3 },
                { -2, 0, 6, 1, -3, 0, 0, 0, 1, -3, -3, 0, -2, -3, 1, -2, 0, -4, -2, -3 },
                { -2, -2, 1, 6, -3, 0, 2, -1, -1, -3, -4, -1, -3, -3, -1, 0, -1, -4, -3, -3 },
                { 0, -3, -3, -3, 9, -3, -4, -3, -3, -1, -1, -3, -1, -2, -3, -1, -1, -2, -2, -1 },
                { -1, 1, 0, 0, -3, 5, 2, -2, 0, -3, -2, 1, 0, -3, -1, 0, -1, -2, -1, -2 },
                { -1, 0, 0, 2, -4, 2, 5, -2, 0, -3, -3, 1, -2, -3, -1, 0, -1, -3, -2, -2 },
                { 0, -2, 0, -1, -3, -2, -2, 6, -2, -4, -4, -2, -3, -3, -2, 0, -2, -2, -3, -3 },
                { -2, 0, 1, -1, -3, 0, 0, -2, 8, -3, -3, -1, -2, -1, -2, -1, -2, -2, 2, -3 },
                { -1, -3, -3, -3, -1, -3, -3, -4, -3, 4, -2, -3, 1, 0, -3, -2, -1, -3, -1, 3 },
                { -1, -2, -3, -4, -1, -2, -3, -4, -3, -2, 4, -2, 2, 0, -3, -2, -1, -2, -1, 1 },
                { -1, 2, 0, -1, -3, 1, 1, -2, -1, -3, -2, 5, -1, -3, -1, 0, -1, -3, -2, -2 },
                { -1, -1, -2, -3, -1, 0, -2, -3, -2, 1, 2, -1, 5, 0, -2, -1, -1, -1, -1, 1 },
                { -2, -3, -3, -3, -2, -3, -3, -3, -1, 0, 0, -3, 0, 6, -4, -2, -2, 1, 3, -1 },
                { -1, -2, -2, -1, -3, -1, -1, -2, -2, -3, -3, -1, -2, -4, 7, -1, -1, -4, -3, -1 },
                { 1, -1, 1, 0, -1, 0, 0, 0, -1, -2, -2, 0, -1, -2, -1, 4, 1, -3, -2, -2 },
                { 0, -1, 0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1, 1, 5, -2, -2, 0 },
                { -3, -3, -4, -4, -2, -2, -3, -2, -2, -3, -2, -3, -1, 1, -4, -3, -2, 11, 2, -3 },
                { -2, -2, -2, -3, -2, -1, -2, -3, 2, -1, -1, -2, -1, 3, -3, -2, -2, 2, 7, -1 },
                { 0, -3, -3, -3, -1, -2, -2, -3, -3, 3, 1, -2, 1, -1, -2, -2, 0, -3, -1, 4 }

        };
        final int[][] nMatrix = { { 1, -5, -5, -1 }, { -5, 1, -1, -5 }, { -5, -1, 1, -5 }, { -1, -5, -5, 1 } };

        final String nc = "ATCG";

        final String aa = "ARNDCQEGHILKMFPSTWYV";

        int[][] M = new int[n][m];
        char[][] S = new char[n][m]; // 记录状态矩阵

        int x1, x2, x3;
        for (int i = 1; i < n; i++) {

            M[i][0] = i * (-2);

        }
        for (int i = 1; i < m; i++) {

            M[0][i] = i * (-2);

        }

        // for (int i = 1; i < n; i++) {
        // for (int j = 1; j < m; j++) {
        // x1 = M[i][j - 1] - 2;
        // x2 = M[i - 1][j] - 2;
        // char a = s.charAt(j - 1);
        // char b = t.charAt(i - 1);
        // int pj = aa.indexOf(a);
        // int pi = aa.indexOf(b);
        // x3 = M[i - 1][j - 1] + BLOSUM62[pi][pj];
        //
        // ArrayList<Integer> status = new ArrayList<Integer>(Arrays.asList(x1, x2,
        // x3));
        // M[i][j] = Collections.max(status);
        // int index = status.indexOf(M[i][j]);
        // if (index == 0) {
        // S[i][j] = '-';
        // } else if (index == 1) {
        // S[i][j] = '|';
        // } else if (index == 2) {
        // S[i][j] = '\\';
        // } else {
        // S[i][j] = '0';
        //
        // }
        // }
        // }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                x1 = M[i][j - 1] - 2;
                x2 = M[i - 1][j] - 2;
                char a = s.charAt(j - 1);
                char b = t.charAt(i - 1);
                int pj = aa.indexOf(a);
                int pi = aa.indexOf(b);
                x3 = M[i - 1][j - 1] + BLOSUM62[pi][pj];

                ArrayList<Integer> status = new ArrayList<Integer>(Arrays.asList(x1, x2, x3));
                M[i][j] = Collections.max(status);
                int index = status.indexOf(M[i][j]);
                if (index == 0) {
                    S[i][j] = '-';
                } else if (index == 1) {
                    S[i][j] = '|';
                } else if (index == 2) {
                    S[i][j] = '\\';
                } else {
                    S[i][j] = '0';

                }
            }
        }

        int i = n - 1; // t
        int j = m - 1; // s

        while (i != 0 || j != 0) {
            if (i == 0) {
                dt.insert(0,"-");
                ds.insert(0, s.charAt(j - 1));
                j--;
                continue;
            }
            if (j == 0) {
                dt.insert(0, t.charAt(i - 1));
                ds.insert(0, "-");
                i--;
                continue;

            }
            if (S[i][j] == '\\') {
                dt.insert(0, t.charAt(i - 1));
                ds.insert(0, s.charAt(j - 1));
                j--;
                i--;
            } else if (S[i][j] == '-') {
                dt.insert(0, "-");
                ds.insert(0, s.charAt(j - 1));
                j--;
            } else {
                dt.insert(0, t.charAt(i - 1));
                ds.insert(0, "-");
                i--;

            }
        }
        System.out.println("ds串为： " + ds);
        System.out.println("dt串为： " + dt);

        System.out.println("\n得分矩阵：");

        for (i = 0; i < n; i++) {
            if (i > 0) {
                System.out.print(t.charAt(i - 1) + "  ");
            } else {
                System.out.print("  ");
            }

            for (j = 0; j < m; j++) {
                System.out.print(String.format("%3d", M[i][j]) + " ");
            }
            System.out.println();
        }
        System.out.println("\n状态转移矩阵：");
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                System.out.print(S[i][j] + " ");
            }
            System.out.println();
        }

    }

}
