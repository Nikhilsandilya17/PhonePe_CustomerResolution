import model.Issue;
import model.IssueStatus;
import model.IssueType;

import java.util.Arrays;
import java.util.List;

public class InterviewDemo {
    public static void main(String[] args) {
        AgentService agentService = new AgentService();
        IssueService issueService = new IssueService(agentService);

        // Add Agents
        agentService.addAgent("agent1@test.com", "Agent1", Arrays.asList(IssueType.PAYMENT, IssueType.GOLD));
        agentService.addAgent("agent2@test.com", "Agent2", Arrays.asList(IssueType.INSURANCE));


        Issue issue1 = issueService.createIssue("T1", IssueType.PAYMENT, "Payment Failed", "Failed on UPI", "cust1@email.com");
        Issue issue2 = issueService.createIssue("T2",IssueType.INSURANCE, "Insurance Pending", "Policy stuck", "cust2@email.com");
        Issue issue3 = issueService.createIssue("T3", IssueType.GOLD, "Gold Price Fluctuation", "Price not updated", "cust1@email.com");
        issueService.assignIssue(issue1.getIssueId());

        issueService.assignIssue(issue2.getIssueId());
        issueService.assignIssue(issue3.getIssueId());

        List<Issue> issues = issueService.getIssuesByEmail("cust1@email.com");
        printIssues(issues);

        issueService.updateIssue(issue1, IssueStatus.IN_PROGRESS, "Waiting for Payment Confirmation");

        issueService.resolveIssue(issue1, "PaymentFailed debited amount will get reverted");

        agentService.viewAgentsWorkHistory();
    }

    private static void printIssues(List<Issue> issues) {
        for(Issue issue : issues) {
            System.out.println("Issue ID: " + issue.getIssueId() + ", Subject: " + issue.getSubject());
        }
    }


}
