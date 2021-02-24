package com.xib.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

import javax.persistence.Column;

public class AgentDto {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class NewAgent{
        @NonNull
        private String firstName;
        @NonNull
        private String lastName;
        @NonNull
        private String idNumber;
        @NonNull
        private Long teamId;
        @NonNull
        private Long managerId;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class PaginatedAgent{
        private Long id;
        private String firstName;
        private String lastName;
        private Long teamId;
        private String teamName;
        private Long managerId;
        private String managerName;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class AgentAssignment{
        private Long id;
        private Long teamId;
    }
}
