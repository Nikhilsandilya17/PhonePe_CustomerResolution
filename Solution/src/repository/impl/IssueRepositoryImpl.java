package repository.impl;

import enums.IssueType;
import model.Issue;
import repository.IssueRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IssueRepositoryImpl implements IssueRepository {

    private final Map<String, Issue> issues;

    public IssueRepositoryImpl() {
        this.issues = new HashMap<>();
    }

    @Override
    public void createIssue(Issue issue) {
        System.out.println("Issue " + issue.getIssueId() + " created for transaction: " + issue.getTransaction().getTransactionId());
        issues.put(issue.getIssueId(), issue);
    }

    @Override
    public Issue findIssueById(String issueId) {
        return issues.get(issueId);
    }

    @Override
    public List<Issue> getAllIssues() {
        return new ArrayList<>(issues.values());
    }
}