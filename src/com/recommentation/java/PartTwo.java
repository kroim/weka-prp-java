package com.recommentation.java;

import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by KUN on 3/16/2017.
 */
public class PartTwo {
    private static UImatrix obj_UImatrix = new UImatrix();
    //private static int[][] ui_matrix = obj_UImatrix.creatUImatrix();
    private static int users = obj_UImatrix.users;
    private static int items = obj_UImatrix.items;
    private static int item_pair = 5;
//    public static double[][] item_based_similarity_matrix(int[][] matrix){
//
//        double[][] sim_mat = new double[items][items];
//        for(int i = 0; i<items; i++){
//            for(int j = 0; j <items; j++){
//                int R_i_sum = 0;
//                int R_j_sum = 0;
//                double R_i_avg = 0.0;
//                double R_j_avg = 0.0;
//                int item_pair_count = 0;
//                for(int k = 0; k < users; k++){
//                    R_i_sum += matrix[k][i];
//                    R_j_sum += matrix[k][j];
//                    if(matrix[k][i] != 0 && matrix[k][j] != 0) item_pair_count++;
//                }
//                R_i_avg = R_i_sum / users;
//                R_j_avg = R_j_sum / users;
//                if(item_pair_count>4){
//                    double sim_A = 0;
//                    double sim_B = 0;
//                    double sim_C = 0;
//                    for(int k = 0; k < users; k++){
//                        if(matrix[k][i] != 0 && matrix[k][j] != 0){
//                            sim_A += Math.pow((matrix[k][i] - R_i_avg),2);
//                            sim_B += Math.pow((matrix[k][j] - R_j_avg),2);
//                            sim_C += (matrix[k][i] - R_i_avg) * (matrix[k][j] - R_j_avg);
//                        }
//                    }
//                    sim_mat[i][j] = sim_C / (Math.sqrt(sim_A) * Math.sqrt(sim_B));
//                }
//                else sim_mat[i][j] = 0;
//            }
//        }
//        return sim_mat;
//    }

    private static int cluster_num = 0;
    //private static String Filename = obj_Cluster_2.UserFilePath;
    private static ArrayList<Integer> cluster_2[] = new ArrayList[100];

    public PartTwo(int clusternum, String CFilePath){
        cluster_num = clusternum;
        cluster_2 = new ArrayList[cluster_num];
        for(int i=0; i<cluster_num; i++)
        {
            cluster_2[i] = new ArrayList<Integer>();
        }
        Cluster obj_Cluster_2 = new Cluster();
        cluster_2 = obj_Cluster_2.create_Cluster(cluster_num, CFilePath);
    }
    public static int find_target_cluster_2(int target_user){
        for(int i = 0; i < cluster_num; i++){
            for(int j = 0; j < cluster_2[i].size(); j++){
                if(cluster_2[i].get(j) == target_user) return i;
            }
        }
        System.out.println("The user id is not exist! Please input number of target user correctly !");
        System.exit(1);
        return 111;
    }
    public static ArrayList<Pair<Integer,Double>> Prate_PartTwo(int target_user, int[][] ui_matrix){
        ArrayList<Pair<Integer, Double>> result = new ArrayList<>();
//        double[][] sim_matrix = item_based_similarity_matrix(ui_matrix);
        double[][] sim_matrix = new double[items][items];
        try {
            FileReader reader = new FileReader("a:\\freelancer\\17_03\\k_08_java\\2\\recom_system\\ItemSimilarity.txt");
            Scanner in = new Scanner(reader);
            while (in.hasNextLine()){
                int row_index = in.nextInt();
                int col_index = in.nextInt();
                double value = in.nextDouble();
                in.nextLine();
                sim_matrix[row_index][col_index] = value;

            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        try {
//            FileWriter writer = new FileWriter("ItemSimilarity1.txt", false);
//            for(int i = 0; i < sim_matrix.length; i++){
//                for(int j = 0; j < sim_matrix[0].length; j++){
//                    writer.write(i+"");
//                    writer.write("\r\t");
//                    writer.write(j+"");
//                    writer.write("\r\t");
//                    writer.write(sim_matrix[i][j]+"");
//                    writer.write("\r\n");
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // find cluster id that include target user
        int cluster_id = find_target_cluster_2(target_user);
        // find target items and rated movies for target user
        ArrayList<Integer> target_movies = new ArrayList<Integer>();
        ArrayList<Integer> rated_movies = new ArrayList<Integer>();
        for(int j = 0; j < ui_matrix[0].length; j++){
            if(ui_matrix[target_user][j] == 0){
                target_movies.add(j);
            }
            else rated_movies.add(j);
        }
        for(int t = 0; t < target_movies.size(); t++){
            double sum = 0.0;
            double div = 0.0;
            for(int r = 0; r < rated_movies.size(); r++){
                sum += sim_matrix[target_movies.get(t)][rated_movies.get(r)] * ui_matrix[target_user][rated_movies.get(r)];
                div += sim_matrix[target_movies.get(t)][rated_movies.get(r)];
            }
            double Pvalue = 0.0;
            if(rated_movies.size() == 0) Pvalue = 0.0;
            else if(div == 0.0) Pvalue = 0.0;
//            else Pvalue= sum / rated_movies.size();
            else Pvalue= sum / div;
            result.add(new Pair<Integer, Double>(target_movies.get(t)+1, Pvalue));
        }
        return result;
    }
}
