package com.recommentation.java;

/**
 * Created by KUN on 3/11/2017.
 */
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
public class Cluster {
    public static String UserFilePath = "";
    public static int numclusters = 0;
    public static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

        try {
            inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + filename);
        }

        return inputReader;
    }

    public static ArrayList<Integer>[] create_Cluster(int cluster_num, String UserFilename){
        numclusters = cluster_num;
        UserFilePath = UserFilename;
//        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
        ArrayList<Integer> cluster[] = new ArrayList[cluster_num];
        for(int i=0; i<cluster_num; i++)
        {
            cluster[i] = new ArrayList<Integer>();
        }
        SimpleKMeans kmeans = new SimpleKMeans();

        //kmeans.setSeed(32);
        String dataset = UserFilePath;
        DataSource source = null;
        try {
            source = new DataSource(dataset);
            Instances data = null;
            data = source.getDataSet();
            kmeans.setPreserveInstancesOrder(true);
            kmeans.setNumClusters(cluster_num);
            kmeans.buildClusterer(data);
            //System.out.println(kmeans);
            int[] assignments = new int[0];
            assignments = kmeans.getAssignments();
            int i=0;
            for(int clusterNum : assignments) {

                //System.out.printf("Instance %d -> Cluster %d \n", i+1, clusterNum);
                cluster[clusterNum].add(i);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cluster;

    }
}
