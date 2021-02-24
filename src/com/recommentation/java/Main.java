package com.recommentation.java;

import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        String UserFile = "a:\\freelancer\\17_03\\k_08_java\\2\\ml-100k.user.arff";

        String UIPath = "a:\\freelancer\\17_03\\k_08_java\\2\\u.data";
        String u1base = "a:\\freelancer\\17_03\\k_08_java\\2\\u1.base";
        String u1test = "a:\\freelancer\\17_03\\k_08_java\\2\\u1.test";
        String u2base = "a:\\freelancer\\17_03\\k_08_java\\2\\u2.base";
        String u2test = "a:\\freelancer\\17_03\\k_08_java\\2\\u2.test";
        String u3base = "a:\\freelancer\\17_03\\k_08_java\\2\\u3.base";
        String u3test = "a:\\freelancer\\17_03\\k_08_java\\2\\u3.test";
        String u4base = "a:\\freelancer\\17_03\\k_08_java\\2\\u4.base";
        String u4test = "a:\\freelancer\\17_03\\k_08_java\\2\\u4.test";
        String u5base = "a:\\freelancer\\17_03\\k_08_java\\2\\u5.base";
        String u5test = "a:\\freelancer\\17_03\\k_08_java\\2\\u5.test";
        String uabase = "a:\\freelancer\\17_03\\k_08_java\\2\\u5.base";
        String uatest = "a:\\freelancer\\17_03\\k_08_java\\2\\u5.test";
        System.out.println("Please input Number of Cluster you want !");
        java.util.Scanner input1 = new java.util.Scanner(System.in);
        int NumOfCluster = input1.nextInt();

        System.out.println("Please input target user id you want !");
        java.util.Scanner input2 = new java.util.Scanner(System.in);

        int target_UserId = input2.nextInt();
        int top_n_items = 10;
        double alpha = 0.5;
        Cluster obj1 = new Cluster();
        ArrayList<Integer> Clusters[]= new ArrayList[NumOfCluster];
        for(int i=0; i<NumOfCluster; i++)
        {
            Clusters[i] = new ArrayList<Integer>();
        }
        int targetUserId = target_UserId-1;
        Clusters = obj1.create_Cluster(NumOfCluster, UserFile);
        PartOne obj_PartOne = new PartOne(NumOfCluster, UserFile);
        PartTwo obj_PartTwo = new PartTwo(NumOfCluster, UserFile);
        int tartet_cluster = obj_PartOne.find_target_cluster(targetUserId);
        UImatrix obj_UImatrix = new UImatrix();
        int[][] ui_matrix = obj_UImatrix.creatUImatrix(UIPath);
        System.out.println("---------------------------- Part One -------------------------------------------");

        System.out.println("target user id : " + target_UserId);
        System.out.println("target cluster : " + tartet_cluster);
        System.out.println("The size of cluster that include target user!");
        System.out.println(Clusters[tartet_cluster].size());
        ArrayList<Pair<Integer, Double>> Prate_PartOne_values = new ArrayList<>();
        Prate_PartOne_values.addAll(obj_PartOne.Prate_PartOne(targetUserId, ui_matrix));
        System.out.println("Part One Result : Predict rate for target user : movie id = predict rate");
        System.out.println(Prate_PartOne_values);

        System.out.println("---------------------------- Part Two ---------------------------------- Running ...");

        ArrayList<Pair<Integer, Double>> Prate_PartTwo_values = new ArrayList<>();
        Prate_PartTwo_values.addAll(obj_PartTwo.Prate_PartTwo(targetUserId, ui_matrix));
        System.out.println("Part Two Result : Predict rate for target user : movie id = predict rate");
        System.out.println(Prate_PartTwo_values);


//        System.out.println("---------------------------- Part Final ----------------------------------");
//        System.out.println("Final Part Result : Top n Items for target user : movie id = predict rate");
        Evaluate obj_Evaluate = new Evaluate();
//        System.out.println("\t" + "Category" + "\t\t" + "MAE" +"\t\t\t\t" + "Coverage");
//        obj_Evaluate.first_MAE(u1base, u1test, NumOfCluster, target_UserId);
//        obj_Evaluate.second_MAE(u1base, u1test, NumOfCluster, target_UserId);
        System.out.println("---------------------------- Combine For Evaluation ----------------------------------");

        obj_Evaluate.combine_MAE(uabase, uatest, NumOfCluster, target_UserId);
        System.out.println();
        System.out.println("---------------------------- Part Combine ----------------------------------");
        obj_Evaluate.combine(targetUserId, ui_matrix, NumOfCluster, UserFile);
//        ArrayList<Pair<Integer, Double>> recomment_list = new ArrayList<>();
//        PartTwo obj_PartTwo = new PartTwo();
//        UImatrix obj_UImatrix = new UImatrix();
//        double[][] parttwo_result = obj_PartTwo.item_based_similarity_matrix(obj_UImatrix.creatUImatrix());
//        int a =  5;
//        System.out.println(parttwo_result);
        System.out.println();
    }
}
