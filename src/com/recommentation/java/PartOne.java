package com.recommentation.java;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by KUN on 3/14/2017.
 */
public class PartOne {

    private static int cluster_num = 0;
    //private static String ClusterPath = obj_Cluster.UserFilePath;
    //private static ArrayList<Integer> cluster[] = new ArrayList[cluster_num];
    private static ArrayList<Integer> cluster[] = new ArrayList[100];
    //private static UImatrix obj_UImatrix = new UImatrix();
    //private static String uipath = "a:\\freelancer\\17_03\\k_08_java\\2\\u.data";
    //public static int uimatrix[][] = obj_UImatrix.creatUImatrix(uipath);
    public PartOne(int clusternum, String CFilePath){
        cluster_num = clusternum;
        cluster = new ArrayList[cluster_num];
        for(int i=0; i<cluster_num; i++)
        {
            cluster[i] = new ArrayList<Integer>();
        }
        Cluster obj_Cluster = new Cluster();
        cluster = obj_Cluster.create_Cluster(cluster_num, CFilePath);
    }

    public static int find_target_cluster(int target_user){
        int num = 0;

        for(int i = 0; i < cluster_num; i++){
            for(int j = 0; j < cluster[i].size(); j++){
                if(cluster[i].get(j) == target_user) return i;
            }
        }
        System.out.println("The user id is not exist! Please input number of target user correctly !");
        System.exit(1);
        return 111;
    }
    ///////////////////////////////// Part Two //////////////////////////////////////////////////////
    public static ArrayList<Pair<Integer, Double>> Prate_PartOne(int target_user, int[][] uimat){
        // find cluster id that include target user.
        int cluster_id = find_target_cluster(target_user);
        // find movies that the value is 0 in UImatrix .
        ArrayList<Integer> target_movies = new ArrayList<Integer>();
        for(int j = 0; j < uimat[0].length; j++){
            if(uimat[target_user][j] == 0){
                target_movies.add(j);
            }
        }
        // delete target user in target cluster
        for(int i = 0; i < cluster[cluster_id].size(); i++){
            if(cluster[cluster_id].get(i) == target_user){
                cluster[cluster_id].remove(i);
            }
        }
        // make the target user matrix for predict rate follows specific movies
        int rows = cluster[cluster_id].size();
        int cols = target_movies.size();
        int target_user_matrix[][] = new int[rows][cols];
        for (int ii = 0; ii < rows; ii++){
            for(int jj = 0; jj < cols; jj++){
                target_user_matrix[ii][jj] = uimat[cluster[cluster_id].get(ii)][target_movies.get(jj)];
            }
        }
        // calculate Prate_target_user
        ArrayList<Pair<Integer, Double>> result = new ArrayList<>();
        for(int j = 0; j < cols; j++){
            double sum = 0.0;
            int count = 0;
            for(int i = 0; i < rows; i++){
                sum += target_user_matrix[i][j];
                if(target_user_matrix[i][j] != 0) count++;
            }
            double add_value = 0.0;
            if(count == 0) add_value = 0.0;
            else add_value= sum / count;

            result.add(new Pair<Integer, Double>(target_movies.get(j)+1, add_value));
        }
        return result;
    }
}
