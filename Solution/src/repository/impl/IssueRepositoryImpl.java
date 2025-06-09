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

    private final Map<String, Issue> issues = new HashMap<>();

    private IssueRepositoryImpl() {
    }

    @Override
    public void save(Issue issue) {
        issues.put(issue.getIssueId(), issue);
    }

    @Override
    public Issue findById(String issueId) {
        return issues.get(issueId);
    }

    @Override
    public List<Issue> findByEmail(String email) {
        return issues.values().stream().filter(issue -> email.equals(issue.getCustomerEmail())).collect(Collectors.toList());
    }

    @Override
    public List<Issue> findByType(IssueType type) {
        return issues.values().stream().filter(issue -> type.equals(issue.getType())).collect(Collectors.toList());
    }

    @Override
    public List<Issue> findAll() {
        return new ArrayList<>(issues.values());
    }
}