package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.Entities.Activity;
import com.nk2.unityDoServices.Entities.ActivityFavorite;
import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Entities.UserActivityHistory;
import com.nk2.unityDoServices.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserHistoryServices {

    @Autowired
    private UserActivityHistoryRepository userActivityHistoryRepository;

    @Autowired
    private ActivityFavoriteRepository activityFavoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private ActivityRepository activityRepository;

    public UserActivityHistory setUserHistory(Integer activityId, String email){
        UserActivityHistory userHistory = new UserActivityHistory();
        if(userRepository.existsByEmail(email)){
            User user = userServices.findUserByEmail(email);
            userHistory.setUserId(user);
        }else{
            System.out.println("user id is null");
            userHistory.setUserId(null);
        }
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity is not found"));;
        userHistory.setActivityId(activity);
        return userActivityHistoryRepository.saveAndFlush(userHistory);
    }

    public void setUserFavorite(Integer activityId, String email){
        User user = userServices.findUserByEmail(email);
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity is not found"));
        ActivityFavorite activityFavorite = activityFavoriteRepository.findActivityFavorite(activityId,user.getId());
        if(activityFavorite == null){
            ActivityFavorite activityfavorite = new ActivityFavorite();
            activityfavorite.setUserId(user);
            activityfavorite.setActivityId(activity);
            activityFavoriteRepository.saveAndFlush(activityfavorite);
        }else{
            activityFavoriteRepository.deleteById(activityFavorite.getId());
        }
    }

    public Integer setUserUnFavorite(Integer activityId, String email){
        User user = userServices.findUserByEmail(email);
        ActivityFavorite activityFavorite = activityFavoriteRepository.findActivityFavorite(activityId,user.getId());
        activityFavoriteRepository.deleteById(activityFavorite.getId());
        return activityFavorite.getId();
    }
}
