package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration,Integer> {

    @Query(value = "SELECT r.* from registration r WHERE r.userId = :userId AND r.activityId = :activityId", nativeQuery = true)
    Registration FindAllByActivityAndUserID(Integer activityId, Integer userId);

    @Query(value = "SELECT r.* from registration r WHERE r.userId = :userId", nativeQuery = true)
    List<Registration> FindAllRegistrationFromUserId(Integer userId);

    @Query(value = "SELECT r.* from registration r WHERE r.activityId = :activity", nativeQuery = true)
    List<Registration> FindAllRegistrationFromActivityId(Integer activity);

    @Query(value = "SELECT r.* from registration r WHERE r.activityId = :activityId AND r.status = :status", nativeQuery = true)
    List<Registration> FindAllActivityRegistrationByStatus(Integer activityId, String status);
}
