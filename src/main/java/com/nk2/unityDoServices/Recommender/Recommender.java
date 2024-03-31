package com.nk2.unityDoServices.Recommender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.neighboursearch.LinearNNSearch;

public class Recommender {
//    private static Map<String, Map<String, Double>> createUserItemMatrix(String userCategoryFile, String activitiesFile) throws Exception {
//        Map<String, Map<String, Double>> userItemMatrix = new HashMap<>();
//
//        try (Reader userReader = new FileReader(userCategoryFile);
//             Reader activitiesReader = new FileReader(activitiesFile)) {
//
//            // Read user category ranking data
//            Iterable<CSVRecord> userRecords = CSVFormat.DEFAULT.parse(userReader);
//            for (CSVRecord record : userRecords) {
//                String userId = record.get("userId");
//                String mainCategoryId = record.get("mainCategoryId");
//                Double categoryRank = Double.parseDouble(record.get("categoryRank"));
//
//                // Read activities data and merge based on mainCategoryId
//                Iterable<CSVRecord> activitiesRecords = CSVFormat.DEFAULT.parse(activitiesReader);
//                for (CSVRecord activityRecord : activitiesRecords) {
//                    String activityId = activityRecord.get("activityId");
//                    if (!userItemMatrix.containsKey(userId)) {
//                        userItemMatrix.put(userId, new HashMap<>());
//                    }
//                    userItemMatrix.get(userId).put(activityId, categoryRank);
//                }
//            }
//        }
//
//        return userItemMatrix;
//    }
//
//    private static Instances readCSVToInstances(String filePath) throws IOException {
//        CSVLoader loader = new CSVLoader();
//        loader.setSource(new java.io.File(filePath));
//        return loader.getDataSet();
//    }
//
//    private static void recommenderEngine(int userId, Instances data, int nRecs) throws Exception {
//        // Create k-nearest neighbors model
//        LinearNNSearch knnModel = new LinearNNSearch(data);
//        System.out.println("knnModel");
//        System.out.println(knnModel);
//        // Get user preferences
//        Instance userInstance = data.instance(userId);
//
//
//        // Find nearest neighbors
//        Instances neighbors = knnModel.kNearestNeighbours(userInstance, nRecs);
//        System.out.println(neighbors);
//
//        // Print recommendations
//        for (int i = 0; i < neighbors.numInstances(); i++) {
//            Instance neighbor = neighbors.instance(i);
//            System.out.println((i + 1) + ": " + neighbor.stringValue(0) + ", " + neighbor.stringValue(1) + ", Distance: " + knnModel.getDistances()[i]);
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        String userCategoryRankingFile = "userCategoryRankings.csv";
//        String activitiesFile = "activityWithCategory.csv";
//
//        Map<String, Map<String, Double>> userItemMatrix = createUserItemMatrix(userCategoryRankingFile, activitiesFile);
//        System.out.println("User-Item Matrix:");
//        System.out.println(userItemMatrix);
//
//        int userId = 1; // Specify user ID here
//        int nRecs = 10;
//        recommenderEngine(userId, (Instances) userItemMatrix, nRecs);
//    }
}
