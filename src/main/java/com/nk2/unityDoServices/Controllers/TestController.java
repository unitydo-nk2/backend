package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.DTOs.Activity.ActivityListDTO;
import com.nk2.unityDoServices.Entities.ActivityWithCategory;
import com.nk2.unityDoServices.Entities.UserActivityHistory;
import com.nk2.unityDoServices.Entities.UserCategoryRanking;
import com.nk2.unityDoServices.Repositories.ActivityWithCategoryRepository;
import com.nk2.unityDoServices.Repositories.UserActivityHistoryRepository;
import com.nk2.unityDoServices.Repositories.UserCategoryRankingRepository;
import com.nk2.unityDoServices.Services.CSVDownloadServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    UserCategoryRankingRepository userCategoryRankingRepository;

    @Autowired
    ActivityWithCategoryRepository activityWithCategoryRepository;

    @Autowired
    CSVDownloadServices csvDownloadServices;

    @GetMapping("")
    public void getActivityList() {
//        List<ActivityWithCategory> activityList = activityWithCategoryRepository.findAll();
//        csvDownloadServices.generateActivityWithCategoryCSV(activityList,"activities.csv");
//        List<UserCategoryRanking> userCategoryRankings = userCategoryRankingRepository.findAll();
//        csvDownloadServices.generateAUserCategoryRankingCSV(userCategoryRankings,"../userCategoryRankings.csv");
//        recommend()
    }
}
