package repository.impl;

import model.Agent;
import repository.AgentRepository;

import java.util.*;

public class AgentRepositoryImpl implements AgentRepository {
    private final Map<String, Agent> agents = new HashMap<>();

    @Override
    public void save(Agent agent) {
        agents.put(agent.getEmail(), agent);
    }

    @Override
    public List<Agent> findAll() {
        return new ArrayList<>(agents.values());
    }

    @Override
    public Agent findByEmail(String email) {
        return agents.get(email);
    }
}