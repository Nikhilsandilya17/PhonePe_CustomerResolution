package repository;

import model.Agent;

import java.util.List;

public interface AgentRepository {
    void save(Agent agent);
    List<Agent> findAll();
    Agent findByEmail(String email);
}