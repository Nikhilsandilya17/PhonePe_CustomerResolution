package service.impl;

import enums.IssueStatus;
import enums.IssueType;
import model.Agent;
import model.Issue;
import repository.AgentRepository;
import repository.impl.AgentRepositoryImpl;
import service.AgentService;

import java.util.*;

public class AgentServiceImpl implements AgentService {
    private static AgentService instance;
    private final AgentRepository agentRepository;

    private AgentServiceImpl() {
        agentRepository = new AgentRepositoryImpl();
    }

    public static AgentService getInstance() {
        if (instance == null) {
            instance = new AgentServiceImpl();
        }
        return instance;
    }

    @Override
    public void addAgent(String email, String name, List<IssueType> expertise) {
        Agent agent = new Agent(email, name, expertise);
        agentRepository.save(agent);
        System.out.println("Agent " + name + " created");
    }

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public void viewAgentsWorkHistory() {
        System.out.println("Agents Work History:");
        for (Agent agent : agentRepository.findAll()) {
            System.out.println("Agent: " + agent.getName() + " (" + agent.getEmail() + ")");
            if (agent.getOverallAssignedIssues().isEmpty()) {
                System.out.println("  No issues assigned yet.");
            } else {
                agent.getOverallAssignedIssues().forEach(issue ->
                        System.out.println("  IssueId: " + issue.getIssueId() + ", Status: " + issue.getStatus() + ", Subject: " + issue.getSubject())
                );
            }
        }
    }

    @Override
    public void assignIssueToAgent(Issue issue) {
        for (Agent agent : agentRepository.findAll()) {
            if (agent.getExpertise().contains(issue.getType())) {
                assignToAgent(agent, issue);
                System.out.println("Issue " + issue.getIssueId() + " assigned to agent: " + agent.getName());
                break;
            }
        }
    }

    private void assignToAgent(Agent agent, Issue issue) {
        agent.getAssignedIssues().add(issue);
        issue.setAssignedAgent(agent);
        agent.getOverallAssignedIssues().add(issue);
        issue.setStatus(IssueStatus.ASSIGNED);
    }
}