import model.Agent;
import model.Issue;
import model.IssueStatus;
import model.IssueType;
import model.Transaction;
import model.TransactionStatus;

import java.util.*;
import java.util.function.Predicate;

public class IssueService {

    private Map<String, Issue> issues = new HashMap<>();
    private AgentService agentService;

    public IssueService(AgentService agentService) {
        this.agentService = agentService;
    }

    public Issue createIssue(String transactionId, IssueType type, String subject, String description, String email) {
        Transaction tx = new Transaction(transactionId, TransactionStatus.FAILED);
        String issueId = UUID.randomUUID().toString();
        Issue issue = new Issue(issueId, tx, email, type, subject, description);
        issues.put(issueId, issue);
        System.out.println("Issue " + issueId + " created for transaction: " + transactionId);
        return issue;
    }

    public void assignIssue(String issueId) {
        Issue issue = issues.get(issueId);
        List<Agent> agents = agentService.getAllAgents();

        for (Agent agent : agents) {
            if (agent.getExpertise().contains(issue.getType())) {
                if (agent.isAvailable()) {
                    changeIssueStatusAndAssignToAgent(agent, issue);
                    System.out.println("Issue " + issueId + " assigned to agent : " + agent.getName());
                } else {
                    changeIssueStatusAndAssignToAgent(agent, issue);
                    System.out.println("Issue " + issueId + " added to waitlist of : " + agent.getName());
                }
            }
        }
    }

    private void changeIssueStatusAndAssignToAgent(Agent agent, Issue issue) {
        agent.getAssignedIssues().add(issue);
        issue.setAssignedAgent(agent);
        agent.getOverallAssignedIssues().add(issue);
        issue.setStatus(IssueStatus.ASSIGNED);
    }


    public List<Issue> filterIssues(Predicate<Issue> condition) {
        List<Issue> filteredIssues = new ArrayList<>();
        for (Issue issue : issues.values()) {
            if (condition.test(issue)) {
                filteredIssues.add(issue);
            }
        }
        return filteredIssues;
    }

    public List<Issue> getIssuesByEmail(String email) {
        return filterIssues(issue -> email.equals(issue.getCustomerEmail()));
    }

    public List<Issue> getIssuesByIssueType(IssueType issueType) {
        return filterIssues(issue -> issueType.equals(issue.getType()));
    }

    public void updateIssue(Issue issue, IssueStatus status, String resolution) {
        if (issue != null) {
            issue.setStatus(status);
            issue.setResolution(resolution);
            System.out.println("Issue: "+ issue.getIssueId() + " status updated to "+ status);
        }
    }

    // Resolve issue by agent
    public void resolveIssue(Issue issue, String resolution) {
        issue.setStatus(IssueStatus.RESOLVED);
        issue.setResolution(resolution);
        setResolvedIssues(issue);
        System.out.println("Issue: "+issue.getIssueId()+ " marked resolved");
    }

    private void setResolvedIssues(Issue issue) {
        Agent agent = issue.getAssignedAgent();
        agent.getAssignedIssues().remove(issue);
        agent.getResolvedIssues().add(issue);
    }
}
