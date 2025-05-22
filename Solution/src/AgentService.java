import model.Agent;
import model.Issue;
import model.IssueType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentService {

    private static AgentService instance;

    public static AgentService getInstance(){
        if (instance == null) {
            instance = new AgentService();
        }
        return instance;
    }
    private Map<String, Agent> agents;

    private AgentService() {
        agents = new HashMap<>();
    }

    public void addAgent(String email, String name, List<IssueType> expertise) {
        Agent agent = new Agent(email, name, expertise);
        agents.put(agent.getEmail(), agent);
        System.out.println("Agent "+ agent.getName() + " created");
    }

    public List<Agent> getAllAgents() {
        return new ArrayList<>(agents.values());
    }

    public Agent getAgentById(String agentId) {
        return agents.get(agentId);
    }

    public void viewAgentsWorkHistory() {
        System.out.println("Agents Work History:");
        for (Agent agent : agents.values()) {
            System.out.println("Agent: " + agent.getName() + " (" + agent.getEmail() + ")");
            if (agent.getOverallAssignedIssues().isEmpty()) {
                System.out.println("  No issues assigned yet.");
            } else {
                for (Issue issue : agent.getOverallAssignedIssues()) {
                    System.out.println("  IssueId: " + issue.getIssueId() + ", Status: " + issue.getStatus() + ", Subject: " + issue.getSubject());
                }
            }
        }
    }
}
