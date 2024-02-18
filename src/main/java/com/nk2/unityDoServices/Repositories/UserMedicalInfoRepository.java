package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.UserMedicalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMedicalInfoRepository extends JpaRepository<UserMedicalInfo,Integer> {
}
