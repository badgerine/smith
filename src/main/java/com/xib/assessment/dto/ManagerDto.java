package com.xib.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ManagerDto {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class NewManager{
        private String firstName;
        private String lastName;
        private String idNumber;
        private Long[] teamIds;
    }

}
