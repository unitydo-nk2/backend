package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.RegistrantDetailsDTO;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrantsDetailsMapperServices {
    public List<RegistrantDetailsDTO> mapToRegistrantsDetailsDTO(List<Object[]> resultList) {
        return resultList.stream()
                .map(this::mapToRegistrantsDetailsDTO)
                .collect(Collectors.toList());
    }

    private RegistrantDetailsDTO mapToRegistrantsDetailsDTO(Object[] result) {
        RegistrantDetailsDTO dto = new RegistrantDetailsDTO();
        dto.setUserId(((Number) result[0]).intValue());
        dto.setUsername((String) result[1]);
        dto.setName((String) result[3]);
        dto.setSurName((String) result[4]);
        dto.setNickName((String) result[5]);
        dto.setGender((String) result[7]);
        dto.setStatus((String) result[20]);
        dto.setRegistrationId(((Number) result[21]).intValue());
        dto.setEmail((String) result[6]);
        dto.setDateOfBirth(((Date) result[8]).toLocalDate());
//        dto.setDateOfBirth(((Timestamp) result[8]).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//        dto.setDateOfBirth(((Timestamp)result[8]).toInstant());
        dto.setReligion((String) result[9]);
        dto.setEmergencyPhoneNumber((String) result[13]);
        dto.setTelephoneNumber((String) result[10]);
        dto.setAddress((String) result[11]);
        return dto;
    }
}
