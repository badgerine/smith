package com.xib.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Column;

public class AgentDto {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class NewAgent{
        private String firstName;
        private String lastName;
        private String idNumber;
        private Long teamId;
        private Long managerId;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class AgentAssignment{
        private Long id;
        private Long teamId;
    }
}
