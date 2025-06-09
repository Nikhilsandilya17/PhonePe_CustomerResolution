package service.impl;

import enums.IssueStatus;
import enums.IssueType;
import enums.TransactionStatus;
import model.Agent;
import model.Issue;
import model.Transaction;
import service.IssueService;
import strategy.IssueFilterStrategy;
import strategy.impl.EmailIssueFilterStrategyImpl;
import strategy.impl.TypeIssueFilterStrategyImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class IssueServiceImpl implements IssueService {
    private static IssueService instance;
    private final Map<String, Issue> issues = new HashMap<>();


    public static IssueService getInstance() {
        if (instance == null) {
            instance = new IssueServiceImpl();
        }
        return instance;
    }

    private static void printIssues(List<Issue> issues) {
        for (Issue issue : issues) {
            System.out.println("Issue ID: " + issue.getIssueId() + ", Subject: " + issue.getSubject());
        }
    }

    @Override
    public Issue createIssue(String transactionId, IssueType type, String subject, String description, String email) {
        Transaction tx = new Transaction(transactionId, TransactionStatus.FAILED);
        String issueId = UUID.randomUUID().toString();
        Issue issue = new Issue(issueId, tx, email, type, subject, description);
        issues.put(issueId, issue);
        System.out.println("Issue " + issueId + " created for transaction: " + transactionId);
        return issue;
    }

    // In IssueServiceImpl.java
    private List<Issue> filterIssues(IssueFilterStrategy strategy) {
        List<Issue> filtered = new ArrayList<>();
        for (Issue issue : issues.values()) {
            if (strategy.filter(issue)) {
                filtered.add(issue);
            }
        }
        return filtered;
    }

    @Override
    public List<Issue> getIssuesByEmail(String email) {
        List<Issue> filteredIssues = filterIssues(new EmailIssueFilterStrategyImpl(email));
        printIssues(filteredIssues);
        return filteredIssues;
    }

    @Override
    public List<Issue> getIssuesByIssueType(IssueType issueType) {
        return filterIssues(new TypeIssueFilterStrategyImpl(issueType));
    }

    @Override
    public void updateIssue(Issue issue, IssueStatus status, String resolution) {
        if (issue != null) {
            issue.setStatus(status);
            issue.setResolution(resolution);
            System.out.println("Issue: " + issue.getIssueId() + " status updated to " + status);
        }
    }

    @Override
    public void resolveIssue(Issue issue, String resolution) {
        if (issue == null) return;
        issue.setStatus(IssueStatus.RESOLVED);
        issue.setResolution(resolution);
        Agent agent = issue.getAssignedAgent();
        if (agent != null) {
            agent.getAssignedIssues().remove(issue);
            agent.getResolvedIssues().add(issue);
            issue.setAssignedAgent(null);
        }
        System.out.println("Issue: " + issue.getIssueId() + " marked " + issue.getStatus());
    }
}