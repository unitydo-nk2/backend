package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

import java.time.Instant;

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
    private Instant activityDate;

    @Column(name = "activityEndDate", nullable = false)
    private Instant activityEndDate;

    @Column(name = "registerStartDate", nullable = false)
    private Instant registerStartDate;

    @Column(name = "registerEndDate", nullable = false)
    private Instant registerEndDate;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location locationId;

    @Column(name = "announcementDate", nullable = false)
    private Instant announcementDate;

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
    private Instant lastUpdate;

    @Column(name = "createTime", nullable = false)
    private Instant createTime;

    @Lob
    @Column(name = "activityFormat", nullable = false)
    private String activityFormat;

    public String getActivityFormat() {
        return activityFormat;
    }

    public void setActivityFormat(String activityFormat) {
        this.activityFormat = activityFormat;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
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

    public Instant getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(Instant announcementDate) {
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

    public Instant getRegisterEndDate() {
        return registerEndDate;
    }

    public void setRegisterEndDate(Instant registerEndDate) {
        this.registerEndDate = registerEndDate;
    }

    public Instant getRegisterStartDate() {
        return registerStartDate;
    }

    public void setRegisterStartDate(Instant registerStartDate) {
        this.registerStartDate = registerStartDate;
    }

    public Instant getActivityEndDate() {
        return activityEndDate;
    }

    public void setActivityEndDate(Instant activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    public Instant getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Instant activityDate) {
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