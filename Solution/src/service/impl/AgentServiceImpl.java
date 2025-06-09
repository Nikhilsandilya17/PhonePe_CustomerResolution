package service.impl;

import enums.IssueStatus;
import enums.IssueType;
import exceptions.ExpertAgentNotFoundException;
import model.Agent;
import model.Issue;
import repository.AgentRepository;
import repository.impl.AgentRepositoryImpl;
import service.AgentService;

import java.util.*;

import static constants.ApplicationConstants.AGENTS_WORK_HISTORY;
import static constants.ApplicationConstants.NO_AGENT_FOUND_WITH_EXPERTISE;
import static constants.ApplicationConstants.NO_ISSUES_ASSIGNED_YET;

public class AgentServiceImpl implements AgentService {

    private static final String ADDED_TO_WAITLIST = " added to waitlist of :";
    private static final String ASSIGNED_TO_AGENT = " assigned to agent :";

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
        agentRepository.addAgent(agent);
    }

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.getAllAgents();
    }

    @Override
    public void viewAgentsWorkHistory() {
        System.out.println(AGENTS_WORK_HISTORY);
        for (Agent agent : agentRepository.getAllAgents()) {
            System.out.println("Agent: " + agent.getName());
            if (agent.getAssignedIssuesHistory().isEmpty()) {
                System.out.println(NO_ISSUES_ASSIGNED_YET);
            } else {
                List<Issue> issues = agent.getAssignedIssuesHistory();
                for (Issue issue : issues) {
                    System.out.println(issue.toString());
                }
            }
        }
    }

    @Override
    public void assignIssueToAgent(Issue issue) throws ExpertAgentNotFoundException {
        List<Agent> expertiseAgents = getExpertAgents(issue);
        if (expertiseAgents.isEmpty()) {
            throw new ExpertAgentNotFoundException(NO_AGENT_FOUND_WITH_EXPERTISE + issue.getType());
        }
        for (Agent agent : expertiseAgents) {
            if (agent.isAvailable()) {
                changeIssueStatusAndAssignToAgent(agent, issue, IssueStatus.ASSIGNED);
                System.out.println("Issue " + issue.getIssueId() + ASSIGNED_TO_AGENT + agent.getName());
                return;
            }
        }
        Agent leastWorkLoadAgent = Collections.min(expertiseAgents, Comparator.comparingInt(a -> a.getAssignedIssues().size()));
        changeIssueStatusAndAssignToAgent(leastWorkLoadAgent, issue, IssueStatus.WAITING);
        System.out.println("Issue " + issue.getIssueId() + ADDED_TO_WAITLIST + leastWorkLoadAgent.getName());
    }

    private List<Agent> getExpertAgents(Issue issue) {
        List<Agent> expertiseAgents = new ArrayList<>();
        for (Agent agent : agentRepository.getAllAgents()) {
            if (agent.getExpertise().contains(issue.getType())) {
                expertiseAgents.add(agent);
            }
        }
        return expertiseAgents;
    }

    private void changeIssueStatusAndAssignToAgent(Agent agent, Issue issue, IssueStatus issueStatus) {
        issue.setAssignedAgent(agent);
        issue.setStatus(issueStatus);
        agent.getAssignedIssues().offer(issue);
        agent.getAssignedIssuesHistory().add(issue);
    }
}