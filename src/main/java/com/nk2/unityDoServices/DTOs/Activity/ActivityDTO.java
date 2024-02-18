package com.nk2.unityDoServices.DTOs.Activity;

import com.nk2.unityDoServices.Entities.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private Integer activityId;
    private Integer activityOwner;
    private String activityOwnerUserName;
    private String activityName;
    private String activityBriefDescription;
    private String activityDescription;
    private Instant activityDate;
    private Instant registerStartDate;
    private Instant registerEndDate;
    private Integer amount;
    private Integer locationId;
    private String locationName;
    private String googleMapLink;
    private Instant announcementDate;
    private String activityStatus;
    private Integer isGamification;
    private String suggestionNotes;
    private Integer categoryId;
    private String category;
    private String mainCategory;
    private Instant lastUpdate;
    private Instant createTime;
    private String activityFormat;
    private Instant activityEndDate;

    public String getActivityDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("Asia/Bangkok"));
        return formatter.format(activityDate);
    }

    public String getRegisterStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("Asia/Bangkok"));
        return formatter.format(registerStartDate);
    }

    public String getRegisterEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("Asia/Bangkok"));
        return formatter.format(registerEndDate);
    }

    public String getActivityEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("Asia/Bangkok"));
        return formatter.format(activityEndDate);
    }

    public String getAnnouncementDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("Asia/Bangkok"));
        return formatter.format(announcementDate);
    }

    public String getLastUpdate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneId.of("Asia/Bangkok"));
        return formatter.format(lastUpdate);
    }

    public String getCreateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("Asia/Bangkok"));
        return formatter.format(createTime);
    }

    // Constructor taking an Activity instance
    public ActivityDTO(Activity activity) {
        this.activityId = activity.getId();
        this.activityOwner = activity.getActivityOwner().getId();
        this.activityOwnerUserName = activity.getActivityOwner().getUsername();
        this.activityName = activity.getActivityName();
        this.activityBriefDescription = activity.getActivityBriefDescription();
        this.activityDescription = activity.getActivityDescription();
        this.activityDate = activity.getActivityDate();
        this.registerStartDate = activity.getRegisterStartDate();
        this.registerEndDate = activity.getRegisterEndDate();
        this.amount = activity.getAmount();
        this.locationId = (activity.getLocationId() != null) ? activity.getLocationId().getId() : null;
        this.locationName = (activity.getLocationId() != null) ? activity.getLocationId().getLocationName() : null;
        this.googleMapLink = (activity.getLocationId() != null) ? activity.getLocationId().getGoogleMapLink() : null;
        this.announcementDate = activity.getAnnouncementDate();
        this.activityStatus = activity.getActivityStatus();
        this.isGamification = activity.getIsGamification();
        this.suggestionNotes = activity.getSuggestionNotes();
        this.categoryId = (activity.getCategoryId() != null) ? activity.getCategoryId().getId() : null;
        this.category = (activity.getCategoryId() != null) ? activity.getCategoryId().getCategory() : null;
        this.mainCategory = (activity.getCategoryId() != null && activity.getCategoryId().getMainCategory() != null)
                ? activity.getCategoryId().getMainCategory().getMainCategory()
                : null;
        this.lastUpdate = activity.getLastUpdate();
        this.createTime = activity.getCreateTime();
        this.activityFormat = activity.getActivityFormat();
        this.activityEndDate = activity.getActivityEndDate();
    }

}
