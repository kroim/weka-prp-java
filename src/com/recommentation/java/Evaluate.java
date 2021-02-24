package com.recommentation.java;

import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by KUN on 3/20/2017.
 */
public class Evaluate {
    public static String userPath = "a:\\freelancer\\17_03\\k_08_java\\2\\ml-100k.user.arff";
    // calculation MAE
    public static double[] MAE(double[] base, double[] test, ArrayList<Pair<Integer, Double>> coverage)
    {
        double[] MAE_result = new double[2];
        int size = coverage.size();
        int index = base.length;
        double sum = 0.0;
        int k = 0;
        for(int i = 0; i < index; i++){
//            System.out.println(i + "base\t" + base[i]);
//            System.out.println(i + "test\t" + test[i]);
            sum += (Math.abs(base[i] - test[i]));
            if(base[i] == 0.0) k++;
        }
        MAE_result[0] = sum / index;
        MAE_result[1] = (double)(size - k) / size * 100;
        return MAE_result;
    }

    // calculation Coverage
//    public static double Coverage(ArrayList<Pair<Integer, Double>> coverage)
//    {
//        double coverage_result = 0;
//        int sum = 0;
//        int index = coverage.size();
//        for(int i = 0; i < index; i++){
//
//            if(coverage.get(i).getValue() == 0.0) sum++;
//        }
//        System.out.println("sum mmmmmmmmmmmmmm : " + sum);
//        coverage_result = ((double) (index - sum) / (double) index) * 100;
//        return coverage_result;
//    }
    // First Part Evaluation

    public static ArrayList<Pair<Integer, Double>> first_MAE(String basePath, String testPath, int clusternum, int targetuser) throws FileNotFoundException {
        int targetUserId = targetuser-1;
        PartOne obj_PartOne = new PartOne(clusternum, userPath);
        UImatrix obj_UImatrix = new UImatrix();
        int[][] ratematrix = obj_UImatrix.creatUImatrix(basePath);
        ArrayList<Pair<Integer, Double>> Base_PartOne_values = new ArrayList<>();
        Base_PartOne_values.addAll(obj_PartOne.Prate_PartOne(targetUserId, ratematrix));
        //System.out.println(Base_PartOne_values);
        ////////////////////////// Test Dataset ///////////////////////////////////////
        FileReader reader = new FileReader(testPath);
        Scanner in = new Scanner(reader);
        ArrayList<Pair<Integer, Double>> Test_PartOne_values = new ArrayList<>();
        while (in.hasNextLine()){
            int row_index = in.nextInt();
            if(row_index == targetuser){
                int item = in.nextInt();
                int rate = in.nextInt();
                Test_PartOne_values.add(new Pair<Integer, Double>(item, (double)rate));
            }
            in.nextLine();
        }
        //System.out.println(Test_PartOne_values);
        int index1 = Base_PartOne_values.size();
        //System.out.println(index1);
        int index2 = Test_PartOne_values.size();
        double[] BaseArray = new double[index2];
        double[] Testarray = new double[index2];
        for(int i = 0; i < index2; i++){

            for (int j = 0; j < index1; j++){
                if((int)Base_PartOne_values.get(j).getKey() == (int)Test_PartOne_values.get(i).getKey()){
                    Testarray[i] = (double)Test_PartOne_values.get(i).getValue();
                    BaseArray[i] = (double)Base_PartOne_values.get(j).getValue();
                }
            }
        }
        double[] MAE = MAE(BaseArray, Testarray, Base_PartOne_values);
        //double coverage = Coverage(Base_PartOne_values);
        System.out.println("\t" + "DBCF" + "\t" +MAE[0] + "\t\t" + MAE[1]);
        //System.out.println("\t" + MAE[0] + "\t\t" + MAE[1]);
        //System.out.println(MAE[1]);
        return Base_PartOne_values;
    }

    // Second Part Evaluation

    public static ArrayList<Pair<Integer, Double>> second_MAE(String basePath, String testPath, int clusternum, int targetuser) throws FileNotFoundException {
        int targetUserId = targetuser-1;
        ArrayList<Pair<Integer, Double>> Base_PartTwo_values = new ArrayList<>();
        UImatrix obj_UImatrix = new UImatrix();
        int[][] ui_matrix = obj_UImatrix.creatUImatrix(basePath);
        PartTwo obj_PartTwo = new PartTwo(clusternum, userPath);
        Base_PartTwo_values.addAll(obj_PartTwo.Prate_PartTwo(targetUserId, ui_matrix));
        //System.out.println(Base_PartTwo_values);
        FileReader reader = new FileReader(testPath);
        Scanner in = new Scanner(reader);
        ArrayList<Pair<Integer, Double>> Test_PartTwo_values = new ArrayList<>();
        while (in.hasNextLine()){
            int row_index = in.nextInt();
            if(row_index == targetuser){
                int item = in.nextInt();
                int rate = in.nextInt();
                Test_PartTwo_values.add(new Pair<Integer, Double>(item, (double)rate));
            }
            in.nextLine();
        }
        int index1 = Base_PartTwo_values.size();
        //System.out.println(index1);
        int index2 = Test_PartTwo_values.size();
        double[] BaseArray = new double[index2];
        double[] Testarray = new double[index2];
        for(int i = 0; i < index2; i++){
            for (int j = 0; j < index1; j++){
                if((int)Base_PartTwo_values.get(j).getKey() == (int)Test_PartTwo_values.get(i).getKey()){
                    Testarray[i] = (double)Test_PartTwo_values.get(i).getValue();
                    BaseArray[i] = (double)Base_PartTwo_values.get(j).getValue();
                }
            }
        }
        double[] MAE = MAE(BaseArray, Testarray, Base_PartTwo_values);
        //double coverage = Coverage(Base_PartTwo_values);
        System.out.println("\t" + "IBCF" + "\t" +MAE[0] + "\t\t" + MAE[1]);
        //System.out.println(MAE[1]);
        return Base_PartTwo_values;
    }

    // Third Part Evaluation (Combination One and Two Parts)
// alph = 0.1 beta = 0.9
// alph = 0.2 beta = 0.8
// alph = 0.3 beta = 0.7
// alph = 0.4 beta = 0.6
// alph = 0.5 beta = 0.5
// alph = 0.6 beta = 0.4
// alph = 0.7 beta = 0.3
// alph = 0.8 beta = 0.2
// alph = 0.9 beta = 0.1
    public static double alph = 0;
    public static double bet = 0;
    public static int n = 10 ;
    public static void quickSort(double[] arr, int[] arr_id, int low, int high) {
        if (arr == null || arr.length == 0)
            return;

        if (low >= high)
            return;

        // pick the pivot
        int middle = low + (high - low) / 2;
        double pivot = arr[middle];
        int pivot_id = arr_id[middle];

        // make left < pivot and right > pivot
        int i = low, j = high;
        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }

            while (arr[j] > pivot) {
                j--;
            }

            if (i <= j) {
                double temp = arr[i];
                int temp_id = arr_id[i];
                arr[i] = arr[j];
                arr_id[i] = arr_id[j];
                arr[j] = temp;
                arr_id[j] = temp_id;
                i++;
                j--;
            }
        }

        // recursively sort two sub parts
        if (low < j)
            quickSort(arr, arr_id, low, j);

        if (high > i)
            quickSort(arr, arr_id, i, high);
    }

    public static void combine_MAE(String basePath, String testPath, int clusternum, int targetuser){
        try {
            ArrayList<Pair<Integer, Double>> first_base = first_MAE(basePath, testPath, clusternum, targetuser);
            ArrayList<Pair<Integer, Double>> second_base = second_MAE(basePath, testPath, clusternum, targetuser);
            //double[][] combine = new double[9][first_base.size()];
            ArrayList<Pair<Integer, Double>> combine_values[] = new ArrayList[9];
            for(int i = 0; i < 9; i++){
                combine_values[i] = new ArrayList<Pair<Integer, Double>>();
                for(int j = 0; j < first_base.size(); j++){
                    double alpha = (double)(i + 1) / 10.0;
                    double beta = 1 - alpha;
                    //combine[i][j] = alpha * first_base.get(j).getValue() + beta * second_base.get(j).getValue();
                    double value = alpha * first_base.get(j).getValue() + beta * second_base.get(j).getValue();
                    combine_values[i].add(new Pair<Integer, Double>(first_base.get(j).getKey(),value));
                }
            }
            // Test Dataset
            FileReader reader = new FileReader(testPath);
            Scanner in = new Scanner(reader);
            ArrayList<Pair<Integer, Double>> Test_values = new ArrayList<>();
            while (in.hasNextLine()){
                int row_index = in.nextInt();
                if(row_index == targetuser){
                    int item = in.nextInt();
                    int rate = in.nextInt();
                    Test_values.add(new Pair<Integer, Double>(item, (double)rate));
                }
                in.nextLine();
            }
            int index1 = first_base.size();
            //System.out.println(index1);
            int index2 = Test_values.size();
            double[][] BaseArray = new double[9][index2];
            double[] Testarray = new double[index2];
            double[][] MAE = new double[9][2];
            //ArrayList<Pair<Integer, Double>> combine_values[] = new ArrayList[9];
            int[][] Baseid = new int[9][index2];
            System.out.println("\t" + "alpha" + "\t" + "beta" + "\t\t" + "MAE" +"\t\t\t\t\t" + "Coverage");
            for(int k = 0; k < 9; k++){
                //combine_values[k] = new ArrayList<Pair<Integer, Double>>();
                for(int i = 0; i < index2; i++){

                    //combine_values[k].add(new Pair<Integer, Double>(first_base.get(i).getKey(), combine[k][i]));
                    for (int j = 0; j < index1; j++){

                        if((int)first_base.get(j).getKey() == (int)Test_values.get(i).getKey()){
                            Testarray[i] = (double)Test_values.get(i).getValue();
                            BaseArray[k][i] = (double)combine_values[k].get(j).getValue();
                            Baseid[k][i] = combine_values[k].get(j).getKey();
                            int aaaa = 0;
                        }
                    }
                }
                MAE[k] = MAE(BaseArray[k], Testarray, combine_values[k]);
                System.out.println("\t" + (double)((k+1)*10)/100.0 + "\t\t" + (double)((10-k-1)/10.0) + "\t" + MAE[k][0] + "\t\t" + MAE[k][1]);
            }

///// alpha and beta calculation

            double[] evaluateMAE = new double[9];
            int[] evaluateid = new int[9];
            for(int i = 0; i < 9; i++){
                evaluateid[i] = i+1;
                evaluateMAE[i] = MAE[i][0];
            }
            int low = 0;
            int high = 8;
            quickSort(evaluateMAE,evaluateid,low,high);
            alph = evaluateid[0] / 10.0;
            bet = (10 - evaluateid[0])/10.0;
            System.out.println();
            System.out.println("\t" + "Alpha Value : " + alph + "\t" + "Beta Value : " + bet);
//            System.out.println(Arrays.toString(BaseArray[8]));
//           System.out.println(Arrays.toString(Baseid[8]));
//            quickSort(BaseArray[8], Baseid[8], low, high);
//            System.out.println(Arrays.toString(BaseArray[8]));
//            System.out.println(Arrays.toString(Baseid[8]));
//            int nTop = Baseid[8].length;
//
//            System.out.print("Recommend N Top items : " + n + " : ");
//            for(int i = 0; i < n; i++){
//
//                System.out.print("\t\t" + Baseid[8][nTop - i - 1]);
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void combine(int targetUserId, int[][] matrix, int NumOfCluster, String UserFile)
    {
        PartOne obj_PartOne = new PartOne(NumOfCluster, UserFile);
        PartTwo obj_PartTwo = new PartTwo(NumOfCluster, UserFile);
        ArrayList<Pair<Integer, Double>> Prate_PartOne_values = new ArrayList<>();
        Prate_PartOne_values.addAll(obj_PartOne.Prate_PartOne(targetUserId, matrix));
        ArrayList<Pair<Integer, Double>> Prate_PartTwo_values = new ArrayList<>();
        Prate_PartTwo_values.addAll(obj_PartTwo.Prate_PartTwo(targetUserId, matrix));
        ArrayList<Pair<Integer, Double>> Combine_result = new ArrayList<>();
        int loop = Prate_PartOne_values.size();
        int[] Combine_id = new int[loop];
        double[] Combine_value = new double[loop];
        for(int i = 0; i < loop; i++){
            Combine_id[i] = Prate_PartOne_values.get(i).getKey();
            Combine_value[i] = alph * Prate_PartOne_values.get(i).getValue() + bet * Prate_PartTwo_values.get(i).getValue();
            double value = alph * Prate_PartOne_values.get(i).getValue() + bet * Prate_PartTwo_values.get(i).getValue();
            Combine_result.add(new Pair<Integer, Double>(Prate_PartOne_values.get(i).getKey(), value));
        }
        int low = 0;
        int high = loop - 1;
//        System.out.println(Arrays.toString(Combine_value));
//        System.out.println(Arrays.toString(Combine_id));
        System.out.println(Combine_result);
        quickSort(Combine_value, Combine_id, low, high);
        System.out.print("Recommend N Top items : " + n + " : ");
        for(int i = 0; i < n; i++){

            System.out.print("\t\t" + Combine_id[loop - i - 1]);
            System.out.print(" = " + Combine_value[loop - i - 1]);
        }
    }

}
