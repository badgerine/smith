package com.xib.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

public class ManagerDto {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class NewManager{
        @NonNull
        private String firstName;
        @NonNull
        private String lastName;
        @NonNull
        private String idNumber;
        @NonNull
        private Long[] teamIds;
    }

}
