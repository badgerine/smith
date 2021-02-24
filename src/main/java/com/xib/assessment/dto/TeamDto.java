package com.xib.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class TeamDto {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class ManagedEmptyTeam{
        @NonNull
        private String name;
        @Nullable
        private Long managerId;
    }
}
