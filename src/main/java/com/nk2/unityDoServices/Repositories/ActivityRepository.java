package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.DTOs.ActivityWithStatusDTO;
import com.nk2.unityDoServices.Entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Integer> {

    @Query(value="SELECT a.*, COUNT(ah.userId) as userCount " +
            "FROM activity a " +
            "INNER JOIN userActivityHistory ah ON a.activityId = ah.activityId " +
            "GROUP BY a.activityId " +
            "ORDER BY userCount DESC ", nativeQuery = true)
    List<Object[]> FindAllByFromUserCount();

    @Query(value = "SELECT a.*,r.status " +
            "FROM activity a INNER JOIN registration r " +
            "ON a.activityId = r.activityId " +
            "WHERE r.userId = :userId "+
            "ORDER BY r.registrationDate DESC"
            ,nativeQuery = true)
    List<Object[]> FindActivityRegisteredByUserId(Integer userId);

    @Query(value = "SELECT a.* FROM activity a INNER JOIN registration r " +
            "ON a.activityId = r.activityId " +
            "WHERE r.status = :status " +
            "AND r.userId = :userId " +
            "ORDER BY r.registrationDate DESC"
            ,nativeQuery = true)
    List<ActivityWithStatusDTO> FindActivityByStatusAndUserId(Integer userId, String status);

    @Query(value="SELECT a.*, COUNT(r.userId) as userCount " +
            "FROM activity a " +
            "INNER JOIN registration r ON a.activityId = r.activityId " +
            "GROUP BY a.activityId " +
            "ORDER BY a.activityName ASC ", nativeQuery = true)
    List<Object[]> FindActivityWithRegisterAmount();
}
