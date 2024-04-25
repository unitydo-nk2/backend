package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.User.UserDetailsDTO;
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
            userHistory.setUserId(null);
        }
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity is not found"));;
        userHistory.setActivityId(activity);
        return userActivityHistoryRepository.saveAndFlush(userHistory);
    }

    public void setUserFavorite(Integer activityId){
        UserDetailsDTO user = userServices.getUserByEmail();
        User targetUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                user.getUserId() + " does not exist !!!"));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity is not found"));
        ActivityFavorite activityFavorite = activityFavoriteRepository.findActivityFavorite(activityId,user.getUserId());
        if(activityFavorite == null){
            ActivityFavorite activityfavorite = new ActivityFavorite();
            activityfavorite.setUserId(targetUser);
            activityfavorite.setActivityId(activity);
            activityFavoriteRepository.saveAndFlush(activityfavorite);
        }else{
            activityFavoriteRepository.deleteById(activityFavorite.getId());
        }
    }

    public Integer setUserUnFavorite(Integer activityId){
        UserDetailsDTO user = userServices.getUserByEmail();
        ActivityFavorite activityFavorite = activityFavoriteRepository.findActivityFavorite(activityId,user.getUserId());
        activityFavoriteRepository.deleteById(activityFavorite.getId());
        return activityFavorite.getId();
    }
}
