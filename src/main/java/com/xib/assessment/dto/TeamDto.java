package com.xib.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class TeamDto {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class EmptyTeam{
        private String name;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class ManagedEmptyTeam{
        private String name;
        private Long managerId;
    }

//    @Builder
//    @Getter
//    @AllArgsConstructor
//    public static class ManagedTeam{
//        private String name;
//        private Long managerId;
//        private Long[] agentIds;
//    }
}
