package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.ActivityWithRegistrationNumberDTO;
import com.nk2.unityDoServices.DTOs.ActivityWithStatusDTO;
import com.nk2.unityDoServices.DTOs.ActivityWithUserCountDTO;
import com.nk2.unityDoServices.Repositories.CategoryRepository;
import com.nk2.unityDoServices.Repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityMapperService {
    public List<ActivityWithUserCountDTO> mapToActivityWithUserCountDTO(List<Object[]> resultList) {
        return resultList.stream()
                .map(this::mapToObjectForActivityWithUserCount)
                .collect(Collectors.toList());
    }

    public List<ActivityWithStatusDTO> mapToActivityWithUserStatusDTO(List<Object[]> resultList) {
        return resultList.stream()
                .map(this::mapToObjectForActivityWithStatus)
                .collect(Collectors.toList());
    }

    public List<ActivityWithRegistrationNumberDTO> mapToActivityWithRegistrationNumberDTO(List<Object[]> resultList) {
        return resultList.stream()
                .map(this::mapToActivityWithRegistrationNumberDTO)
                .collect(Collectors.toList());
    }

    private ActivityWithUserCountDTO mapToObjectForActivityWithUserCount(Object[] result) {

        ActivityWithUserCountDTO dto = new ActivityWithUserCountDTO();
        dto.setActivityId(((Number) result[0]).intValue());
        dto.setActivityOwner(((Number) result[1]).intValue());
        dto.setActivityName((String) result[2]);
        dto.setActivityBriefDescription((String) result[3]);
        dto.setActivityDescription((String) result[4]);
        dto.setActivityDate(((Timestamp) result[5]).toInstant());
        dto.setRegisterStartDate(((Timestamp) result[6]).toInstant());
        dto.setRegisterEndDate(((Timestamp) result[7]).toInstant());
        dto.setActivityEndDate(((Timestamp) result[8]).toInstant());
        dto.setAmount(((Number) result[9]).intValue());
        if (result[10] != null) {
            int intValue = ((Number) result[10]).intValue();
            // Use intValue as needed
        } else {
            dto.setLocation(null);
        }
        dto.setAnnouncementDate(((Timestamp) result[11]).toInstant());
        dto.setActivityStatus((String) result[12]);
        dto.setIsGamification(((Number) result[13]).intValue());
        dto.setSuggestionNotes((String) result[14]);
        dto.setCategoryId(((Number) result[15]).intValue());
        dto.setLastUpdate(((Timestamp) result[16]).toInstant());
        dto.setCreateTime(((Timestamp) result[17]).toInstant());
        dto.setActivityFormat((String) result[18]);
        dto.setUserCount(((Number) result[19]).intValue());
        return dto;
    }

    private ActivityWithStatusDTO mapToObjectForActivityWithStatus(Object[] result) {

        ActivityWithStatusDTO dto = new ActivityWithStatusDTO();
        dto.setActivityId(((Number) result[0]).intValue());
        dto.setActivityOwner(((Number) result[1]).intValue());
        dto.setActivityName((String) result[2]);
        dto.setActivityBriefDescription((String) result[3]);
        dto.setActivityDescription((String) result[4]);
        dto.setActivityDate(((Timestamp) result[5]).toInstant());
        dto.setRegisterStartDate(((Timestamp) result[6]).toInstant());
        dto.setRegisterEndDate(((Timestamp) result[7]).toInstant());
        dto.setActivityEndDate(((Timestamp) result[8]).toInstant());
        dto.setAmount(((Number) result[9]).intValue());
        if (result[10] != null) {
            int intValue = ((Number) result[10]).intValue();
            // Use intValue as needed
        } else {
            dto.setLocation(null);
        }
        dto.setAnnouncementDate(((Timestamp) result[11]).toInstant());
        dto.setActivityStatus((String) result[12]);
        dto.setIsGamification(((Number) result[13]).intValue());
        dto.setSuggestionNotes((String) result[14]);
        dto.setCategoryId(((Number) result[15]).intValue());
        dto.setLastUpdate(((Timestamp) result[16]).toInstant());
        dto.setCreateTime(((Timestamp) result[17]).toInstant());
        dto.setActivityFormat((String) result[18]);
        dto.setStatus(((String) result[19]));
        return dto;
    }

    private ActivityWithRegistrationNumberDTO  mapToActivityWithRegistrationNumberDTO (Object[] result) {
        ActivityWithRegistrationNumberDTO dto = new ActivityWithRegistrationNumberDTO();
        dto.setActivityId(((Number) result[0]).intValue());
        dto.setActivityOwnerId(((Number) result[1]).intValue());
        dto.setActivityName((String) result[2]);
        dto.setActivityDate(((Timestamp) result[5]).toInstant());
        dto.setActivityEndDate(((Timestamp) result[8]).toInstant());
        dto.setAmount(((Number) result[9]).intValue());
        dto.setUserRegistration(((Number) result[19]).intValue());
        return dto;
    }

}
