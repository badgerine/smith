package com.xib.assessment.service;

import com.xib.assessment.dao.AgentRepository;
import com.xib.assessment.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;

    public List<Agent> findAgentsPaginated(int pageNo, int pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        Page<Agent> pagedResult = agentRepository.findAll(paging)
                .map(agent -> Agent.builder().id(agent.getId()).firstName(agent.getFirstName())
                .lastName(agent.getLastName()).build());
        if(pagedResult.hasContent()){
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Agent> findAll() {
        List<Agent> results = new ArrayList<>();
        agentRepository.findAll(Sort.by("lastName")).forEach(results::add);
        return results;
    }
}
