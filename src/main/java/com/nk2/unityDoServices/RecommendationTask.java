package com.nk2.unityDoServices;

import com.nk2.unityDoServices.Entities.ActivityWithCategory;
import com.nk2.unityDoServices.Entities.UserCategoryRanking;
import com.nk2.unityDoServices.Repositories.ActivityWithCategoryRepository;
import com.nk2.unityDoServices.Repositories.UserCategoryRankingRepository;
import com.nk2.unityDoServices.Services.CSVDownloadServices;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.FileSystems;
import java.util.List;

@Component
public class RecommendationTask {

    @Autowired
    UserCategoryRankingRepository userCategoryRankingRepository;

    @Autowired
    ActivityWithCategoryRepository activityWithCategoryRepository;

    @Autowired
    CSVDownloadServices csvDownloadServices;

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * ?")
    public void generateCSV(){
        System.out.println("do task");
        List<ActivityWithCategory> activityList = activityWithCategoryRepository.findAll();
        csvDownloadServices.generateActivityWithCategoryCSV(activityList, FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath() +"/activityWithCategory.csv");
        System.out.println("activityWithCategory created @ "+FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath() +"/activityWithCategory.csv");
        List<UserCategoryRanking> userCategoryRankings = userCategoryRankingRepository.findAll();
        csvDownloadServices.generateAUserCategoryRankingCSV(userCategoryRankings,FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()+"/userCategoryRankings.csv");
        System.out.println("userCategoryRankings created @ "+FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath() +"/userCategoryRankings.csv");

    }
}
