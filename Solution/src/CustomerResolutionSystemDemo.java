import enums.IssueStatus;
import enums.IssueType;
import exceptions.IssueNotFoundException;
import model.Issue;
import model.Transaction;
import service.AgentService;
import service.IssueService;
import service.impl.AgentServiceImpl;
import service.impl.IssueServiceImpl;

import java.util.Arrays;
import java.util.List;

import static constants.ApplicationConstants.ERROR_ASSIGNING_ISSUE_TO_AGENT;
import static constants.ApplicationConstants.ERROR_UPDATING_ISSUE;

public class CustomerResolutionSystemDemo {
    public static void main(String[] args) throws IssueNotFoundException {
        AgentService agentService = AgentServiceImpl.getInstance();
        IssueService issueService = IssueServiceImpl.getInstance();

        Transaction transaction1 = new Transaction("T1");
        Transaction transaction2 = new Transaction("T2");
        Transaction transaction3 = new Transaction("T3");

        agentService.addAgent("agent1@test.com", "Agent1", Arrays.asList(IssueType.PAYMENT, IssueType.GOLD));
        agentService.addAgent("agent2@test.com", "Agent2", List.of(IssueType.INSURANCE, IssueType.MUTUAL_FUNDS));
        agentService.addAgent("agent3@test.com", "Agent3", List.of(IssueType.MUTUAL_FUNDS));

        Issue issue1 = issueService.createIssue(transaction1.getTransactionId(), IssueType.PAYMENT, "Payment Failed", "My payment failed but money is debited", "cust1@email.com");
        Issue issue2 = issueService.createIssue(transaction2.getTransactionId(), IssueType.MUTUAL_FUNDS, "Purchase Failed", "Unable to purchase Mutual Fund", "cust2@email.com");
        Issue issue3 = issueService.createIssue(transaction3.getTransactionId(), IssueType.GOLD, "Gold Price not reflecting", "Price not updated", "cust1@email.com");

        try {
            agentService.assignIssueToAgent(issue1);
        } catch (Exception e) {
            System.out.println(ERROR_ASSIGNING_ISSUE_TO_AGENT + e.getMessage());
        }
        try {
            agentService.assignIssueToAgent(issue2);
        } catch (Exception e) {
            System.out.println(ERROR_ASSIGNING_ISSUE_TO_AGENT + e.getMessage());
        }
        try {
            agentService.assignIssueToAgent(issue3);
        } catch (Exception e) {
            System.out.println(ERROR_ASSIGNING_ISSUE_TO_AGENT + e.getMessage());
        }

        issueService.getIssuesByEmail("cust1@email.com");
        issueService.getIssuesByIssueType(IssueType.MUTUAL_FUNDS);

        try {
            issueService.updateIssue(issue1.getIssueId(), IssueStatus.IN_PROGRESS, "Waiting for Payment Confirmation");
        } catch (Exception e) {
            System.out.println(ERROR_UPDATING_ISSUE + e.getMessage());
        }
        try {
            issueService.updateIssue("1234", IssueStatus.IN_PROGRESS, "Waiting for Payment Confirmation");
        } catch (Exception e) {
            System.out.println(ERROR_UPDATING_ISSUE + e.getMessage());
        }

        issueService.resolveIssue(issue1.getIssueId(), "PaymentFailed debited amount will get reverted");

        agentService.viewAgentsWorkHistory();
    }

}
