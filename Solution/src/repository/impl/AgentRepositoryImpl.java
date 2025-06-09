package repository.impl;

import model.Agent;
import repository.AgentRepository;

import java.util.*;

public class AgentRepositoryImpl implements AgentRepository {
    private final Map<String, Agent> agents;

    public AgentRepositoryImpl() {
        this.agents = new HashMap<>();
    }

    @Override
    public void addAgent(Agent agent) {
        agents.put(agent.getEmail(), agent);
        System.out.println("Agent: " + agent.getName() + " Created");
    }

    @Override
    public List<Agent> getAllAgents() {
        return new ArrayList<>(agents.values());
    }

}