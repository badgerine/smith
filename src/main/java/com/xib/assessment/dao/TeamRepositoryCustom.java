package com.xib.assessment.dao;

import com.xib.assessment.model.Team;

import java.util.List;

public interface TeamRepositoryCustom {
    List<Team> findTeamsByEmptyMembership();
}
