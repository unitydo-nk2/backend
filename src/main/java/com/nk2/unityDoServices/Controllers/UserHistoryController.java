package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.Entities.UserActivityHistory;
import com.nk2.unityDoServices.Services.UserHistoryServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracks")
public class UserHistoryController {
    @Autowired
    private UserHistoryServices userHistoryServices;

    @PostMapping("/{activityId}")
    public UserActivityHistory setUserHistory(@PathVariable Integer activityId, @Valid @RequestBody String email) {
        return userHistoryServices.setUserHistory(activityId,email);
    }

    @PostMapping("/favorite/{activityId}")
    public void setUserFavorite(@PathVariable Integer activityId) {
        userHistoryServices.setUserFavorite(activityId);
    }

    @PatchMapping("/unFavorite/{activityId}")
    public Integer setUserUnFavorite(@PathVariable Integer activityId) {
        return userHistoryServices.setUserUnFavorite(activityId);
    }
}