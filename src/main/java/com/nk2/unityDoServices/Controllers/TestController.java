package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.DTOs.Activity.ActivityCardSliderListDTO;
import com.nk2.unityDoServices.DTOs.Activity.ActivityListDTO;
import com.nk2.unityDoServices.DTOs.Activity.ActivityReceiverDTO;
import com.nk2.unityDoServices.Entities.ActivityWithCategory;
import com.nk2.unityDoServices.Entities.UserActivityHistory;
import com.nk2.unityDoServices.Entities.UserCategoryRanking;
import com.nk2.unityDoServices.Repositories.ActivityWithCategoryRepository;
import com.nk2.unityDoServices.Repositories.UserActivityHistoryRepository;
import com.nk2.unityDoServices.Repositories.UserCategoryRankingRepository;
import com.nk2.unityDoServices.Services.ActivityServices;
import com.nk2.unityDoServices.Services.CSVDownloadServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
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

    @Autowired
    ActivityServices activityServices;

//    @GetMapping("/no")
//    public String getActivityList() {
//        String url = "http://127.0.0.1:5000/api/recommendActivities/10";
//
//        // Create a RestTemplate instance
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Fetch data from the URL
//        String result = restTemplate.getForObject(url, String.class);
//
//        // Return the fetched data
//        return result;
//    }
//
//    @GetMapping("")
//    public List<ActivityCardSliderListDTO> fetchActivities() {
//        // URL to fetch data from
//        String url = "http://127.0.0.1:5000/api/recommendActivities/10";
//
//        // Create a RestTemplate instance
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Fetch data from the URL and deserialize into a list of Activity objects
//        ResponseEntity<ActivityReceiverDTO[]> response = restTemplate.getForEntity(url, ActivityReceiverDTO[].class);
//        List<ActivityReceiverDTO> activities = Arrays.asList(response.getBody());
//
//        // Return the list of activities
//        return activityServices.getRecommendsActivity(activities) ;
//    }


}
