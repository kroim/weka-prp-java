package com.recommentation.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class UImatrix {
    public static int users = 943;
    public static int items = 1682;
    public static int[][] creatUImatrix(String path){

        int[][] ui_mat = new int[users][items];
        for(int i = 0; i<users; i++){
            for(int j = 0; j <items; j++){
                ui_mat[i][j] = 0;
            }
        }
        //path = "a:\\freelancer\\17_03\\k_08_java\\2\\u.data";
        try {
            FileReader reader = new FileReader(path);
            Scanner in = new Scanner(reader);
            while (in.hasNextLine()){
                int row_index = in.nextInt();
                int col_index = in.nextInt();
                int value = in.nextInt();
                in.nextLine();
                ui_mat[row_index-1][col_index-1] = value;

            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return ui_mat;
    }
}
