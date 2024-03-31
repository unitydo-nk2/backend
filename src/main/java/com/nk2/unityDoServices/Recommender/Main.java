package com.nk2.unityDoServices.Recommender;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import weka.core.*;
import weka.core.neighboursearch.LinearNNSearch;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static void recommenderEngine(int userId, Map<String, Map<String, Double>> userItemMatrix, int nRecs) throws Exception {
        // Convert userItemMatrix data to Weka Instances
        Instances data = createUserItemInstances(userItemMatrix);

        // Create k-nearest neighbors model
        LinearNNSearch knnModel = new LinearNNSearch(data);
        System.out.println("knnModel");
        System.out.println(knnModel);

        // Get user preferences
        Instance userInstance = data.instance(userId);

        // Find nearest neighbors
        Instances neighbors = knnModel.kNearestNeighbours(userInstance, nRecs);
        System.out.println(neighbors);

        // Print recommendations
        for (int i = 0; i < neighbors.numInstances(); i++) {
            Instance neighbor = neighbors.instance(i);
            System.out.println((i + 1) + ": " + neighbor.stringValue(0) + ", " + neighbor.stringValue(1) + ", Distance: " + knnModel.getDistances()[i]);
        }
    }
    private static Instances createUserItemInstances(Map<String, Map<String, Double>> userItemMatrix) {
        Instances data = null;
        // Initialize attributes list
        FastVector attributes = new FastVector();

        // Create a mapping between item IDs and attribute indices
        Map<String, Integer> itemIndexMap = new HashMap<>();

        // Add a nominal attribute for user ID
        attributes.addElement(new Attribute("userId", true));

        // Create attributes for each item
        int index = 1; // Start index from 1 to skip the user ID attribute
        for (String itemId : userItemMatrix.keySet()) {
            attributes.addElement(new Attribute(itemId));
            itemIndexMap.put(itemId, index++);
        }

        // Create Instances object with the defined attributes
        data = new Instances("UserItemMatrix", attributes, 0);

        // Add instances (user-item ratings) to the Instances object
        for (Map.Entry<String, Map<String, Double>> userEntry : userItemMatrix.entrySet()) {
            String userId = userEntry.getKey();
            Map<String, Double> itemRatings = userEntry.getValue();

            // Create a new instance
            Instance instance = new DenseInstance(data.numAttributes());
            instance.setValue((Attribute) attributes.elementAt(0), userId); // Set user ID

            // Set values for item attributes
            for (Map.Entry<String, Double> itemEntry : itemRatings.entrySet()) {
                String itemId = itemEntry.getKey();
                Double rating = itemEntry.getValue();
                int attrIndex = itemIndexMap.get(itemId); // Retrieve the attribute index from the map
                instance.setValue(attrIndex, rating);
            }

            // Add the instance to the Instances object
            data.add(instance);
        }

        return data;
    }

    private static Map<String, Map<String, Double>> createUserItemMatrix(String userCategoryFile, String activitiesFile) throws Exception {
        Map<String, Map<String, Double>> userItemMatrix = new HashMap<>();

        // Read user category ranking data
        try (Reader userReader = new FileReader(userCategoryFile);
             Reader activitiesReader = new FileReader(activitiesFile)) {

            // Define CSV format with header mapping
            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader();

            // Parse user category ranking data
            CSVParser userParser = new CSVParser(userReader, csvFormat);
            for (CSVRecord userRecord : userParser) {
                String userId = userRecord.get("userId");
                String mainCategoryId = userRecord.get("mainCategoryId");
                Double categoryRank = Double.parseDouble(userRecord.get("categoryRank"));

                // Read activities data and merge based on mainCategoryId
                CSVParser activitiesParser = new CSVParser(activitiesReader, csvFormat);
                for (CSVRecord activityRecord : activitiesParser) {
                    String activityId = activityRecord.get("activityId");
                    if (!userItemMatrix.containsKey(userId)) {
                        userItemMatrix.put(userId, new HashMap<>());
                    }
                    userItemMatrix.get(userId).put(activityId, categoryRank);
                }
            }
        }

        return userItemMatrix;
    }

    public static void main(String[] args) throws Exception {
        String userCategoryRankingFile = "userCategoryRankings.csv";
        String activitiesFile = "activityWithCategory.csv";

        Map<String, Map<String, Double>> userItemMatrix = createUserItemMatrix(userCategoryRankingFile, activitiesFile);

        System.out.println(userItemMatrix);
        int userId = 1; // Specify user ID here
        int nRecs = 10;
        recommenderEngine(userId, userItemMatrix, nRecs);
    }
}
