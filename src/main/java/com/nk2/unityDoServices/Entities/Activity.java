package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "activity", indexes = {
        @Index(name = "fk_Activity_location1_idx", columnList = "locationId"),
        @Index(name = "fk_Activity_Category1_idx", columnList = "categoryId"),
        @Index(name = "fk_Activity_User1_idx", columnList = "activityOwner")
})
@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activityId", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "activityOwner", nullable = false)
    private User activityOwner;

    @Column(name = "activityName", nullable = false, length = 150)
    private String activityName;

    @Column(name = "activityBriefDescription", nullable = false, length = 100)
    private String activityBriefDescription;

    @Column(name = "activityDescription", nullable = false, length = 300)
    private String activityDescription;

    @Column(name = "activityDate", nullable = false)
    private LocalDateTime activityDate;

    @Column(name = "activityEndDate", nullable = false)
    private LocalDateTime activityEndDate;

    @Column(name = "registerStartDate", nullable = false)
    private LocalDateTime registerStartDate;

    @Column(name = "registerEndDate", nullable = false)
    private LocalDateTime registerEndDate;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location locationId;

    @Column(name = "announcementDate", nullable = false)
    private LocalDateTime announcementDate;

    @Lob
    @Column(name = "activityStatus", nullable = false)
    private String activityStatus;

    @Column(name = "isGamification", nullable = false)
    private Integer isGamification;

    @Column(name = "suggestionNotes", length = 500)
    private String suggestionNotes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category categoryId;

    @Column(name = "lastUpdate", nullable = false)
    private LocalDateTime lastUpdate;

    @Column(name = "createTime", nullable = false)
    private LocalDateTime createTime;

    @Lob
    @Column(name = "activityFormat", nullable = false)
    private String activityFormat;

    public String getActivityFormat() {
        return activityFormat;
    }

    public void setActivityFormat(String activityFormat) {
        this.activityFormat = activityFormat;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public String getSuggestionNotes() {
        return suggestionNotes;
    }

    public void setSuggestionNotes(String suggestionNotes) {
        this.suggestionNotes = suggestionNotes;
    }

    public Integer getIsGamification() {
        return isGamification;
    }

    public void setIsGamification(Integer isGamification) {
        this.isGamification = isGamification;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public LocalDateTime getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(LocalDateTime announcementDate) {
        this.announcementDate = announcementDate;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getRegisterEndDate() {
        return registerEndDate;
    }

    public void setRegisterEndDate(LocalDateTime registerEndDate) {
        this.registerEndDate = registerEndDate;
    }

    public LocalDateTime getRegisterStartDate() {
        return registerStartDate;
    }

    public void setRegisterStartDate(LocalDateTime registerStartDate) {
        this.registerStartDate = registerStartDate;
    }

    public LocalDateTime getActivityEndDate() {
        return activityEndDate;
    }

    public void setActivityEndDate(LocalDateTime activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityBriefDescription() {
        return activityBriefDescription;
    }

    public void setActivityBriefDescription(String activityBriefDescription) {
        this.activityBriefDescription = activityBriefDescription;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public User getActivityOwner() {
        return activityOwner;
    }

    public void setActivityOwner(User activityOwner) {
        this.activityOwner = activityOwner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}