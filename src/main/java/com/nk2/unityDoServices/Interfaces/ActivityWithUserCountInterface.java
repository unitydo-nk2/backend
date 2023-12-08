package com.nk2.unityDoServices.Interfaces;

import java.time.Instant;

public interface ActivityWithUserCountInterface {
    Integer getId();
    Integer getOwnerId();
    String getActivityName();
    String getActivityBriefDescription();
    String getActivityDescription();
    Instant getActivityDate();
    Instant getRegisterStartDate();
    Instant getRegisterEndDate();
    Integer getAmount();
    Integer getLocationId();
    Instant getAnnouncementDate();
    String getActivityStatus();
    Integer getIsGamification();
    String getSuggestionNotes();
    Integer getCategoryId();
    Instant getLastUpdate();
    Instant getCreateTime();
    String getActivityFormat();
    Instant getActivityEndDate();
    Integer getUserCount();
}
