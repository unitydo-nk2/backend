package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.Entities.ActivityWithCategory;
import com.nk2.unityDoServices.Entities.UserCategoryRanking;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CSVDownloadServices {

    private void writeActivityWithCategoryToCSV(List<ActivityWithCategory> activities, String fileName) throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(fileName), CSVFormat.DEFAULT)) {
            printer.printRecord("activityId", "activityName", "CategoryId", "mainCategoryId","mainCategory");
            for (ActivityWithCategory activity : activities) {
                printer.printRecord(
                        activity.getActivityId(),
                        activity.getActivityName(),
                        activity.getCategoryId(),
                        activity.getMainCategoryId(),
                        activity.getMainCategory()
                );
            }
        }
    }

    private void writeUserCategoryRankingToCSV(List<UserCategoryRanking> users, String fileName) throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(fileName), CSVFormat.DEFAULT)) {
            printer.printRecord("favoriteCategoryId", "userId", "age", "mainCategoryId","categoryRank");
            for (UserCategoryRanking user : users) {
                printer.printRecord(user.getFavoriteCategoryId(),
                        user.getUserId(),
                        user.getAge(),
                        user.getMainCategoryId(),
                        user.getCategoryRank()
                );
            }
        }
    }

    public void generateActivityWithCategoryCSV(List<ActivityWithCategory> activities, String fileName) {
        try {
            writeActivityWithCategoryToCSV(activities, fileName);
        }catch(FileAlreadyExistsException e){
            try{
                Files.delete(Paths.get(fileName)); // Delete the existing file
                writeActivityWithCategoryToCSV(activities, fileName);
            }catch(IOException ex){
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateAUserCategoryRankingCSV(List<UserCategoryRanking> users, String fileName) {
        try {
            writeUserCategoryRankingToCSV(users, fileName);
        }catch(FileAlreadyExistsException e){
            try{
                Files.delete(Paths.get(fileName)); // Delete the existing file
                writeUserCategoryRankingToCSV(users, fileName);
            }catch(IOException ex){
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
