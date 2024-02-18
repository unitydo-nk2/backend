package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.MedicalInfomation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalInformationRepository extends JpaRepository<MedicalInfomation,Integer> {
}
