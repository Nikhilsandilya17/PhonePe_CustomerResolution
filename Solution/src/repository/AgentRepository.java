package repository;

import model.Agent;

import java.util.List;

public interface AgentRepository {
    void addAgent(Agent agent);

    List<Agent> getAllAgents();

}