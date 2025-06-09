import enums.IssueStatus;
import enums.IssueType;
import model.Issue;
import service.AgentService;
import service.IssueService;
import service.impl.AgentServiceImpl;
import service.impl.IssueServiceImpl;

import java.util.Arrays;
import java.util.List;

public class CustomerResolutionSystemDemo {
    public static void main(String[] args) {
        AgentService agentService = AgentServiceImpl.getInstance();
        IssueService issueService = IssueServiceImpl.getInstance();

        agentService.addAgent("agent1@test.com", "Agent1", Arrays.asList(IssueType.PAYMENT, IssueType.GOLD));
        agentService.addAgent("agent2@test.com", "Agent2", List.of(IssueType.INSURANCE));

        Issue issue1 = issueService.createIssue("T1", IssueType.PAYMENT, "Payment Failed", "Failed on UPI", "cust1@email.com");
        Issue issue2 = issueService.createIssue("T2", IssueType.INSURANCE, "Insurance Pending", "Policy stuck", "cust2@email.com");
        Issue issue3 = issueService.createIssue("T3", IssueType.GOLD, "Gold Price Fluctuation", "Price not updated", "cust1@email.com");

        agentService.assignIssueToAgent(issue1);
        agentService.assignIssueToAgent(issue2);
        agentService.assignIssueToAgent(issue3);

        List<Issue> issues = issueService.getIssuesByEmail("cust1@email.com");

        issueService.updateIssue(issue1, IssueStatus.IN_PROGRESS, "Waiting for Payment Confirmation");
        issueService.resolveIssue(issue1, "PaymentFailed debited amount will get reverted");

        agentService.viewAgentsWorkHistory();
    }

}