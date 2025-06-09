package service;

import enums.IssueType;
import model.Agent;
import model.Issue;

import java.util.List;

public interface AgentService {
    void addAgent(String email, String name, List<IssueType> expertise);

    List<Agent> getAllAgents();

    void viewAgentsWorkHistory();

    void assignIssueToAgent(Issue issue);
}