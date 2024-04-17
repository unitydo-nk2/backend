package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Activity;
import com.nk2.unityDoServices.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User>  findByEmail(String Email);
    Optional<User> findUserByEmail(String email);
    public boolean existsByEmail(String Email);

    @Query(value="SELECT * " +
            "FROM user u " +
            "WHERE u.status = 'Active' ", nativeQuery = true)
    List<User> findAllActiveUser();
    @Query(value = "select u.* from user u where u.username = :username", nativeQuery = true)
    User findUserbyUserName (String username);
    @Query(value = "select u.* from user u inner join registration r on u.userId = r.userid where r.activityId = :activityId", nativeQuery = true)
    List<User> findRegisteredUserFromActivityId (Integer activityId);
    @Query(value = "select u.* , r.status, r.registrationId, r.registrationDate from user u inner join registration r on u.userId = r.userid where r.activityId = :activityId", nativeQuery = true)
    List<Object[]> findRegisteredUserWithStatusFromActivityId (Integer activityId);
    @Query(value = "select u.* , r.status, r.registrationId, r.registrationDate from user u inner join registration r on u.userId = r.userid where r.registrationId = :registrationId", nativeQuery = true)
    List<Object[]> findRegisteredUserWithStatusFromRegistrationId (Integer registrationId);
    @Query(value = "select u.* from user u join activity a on u.userId = a.activityOwner on a.activityId = :activityId", nativeQuery = true)
    User findActivityOwner (Integer activityId);
}