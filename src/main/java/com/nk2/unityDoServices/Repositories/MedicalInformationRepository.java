package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Medicalinfomation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalInformationRepository extends JpaRepository<Medicalinfomation,Integer> {
}
