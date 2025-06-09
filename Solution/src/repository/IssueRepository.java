package repository;

import model.Issue;

import java.util.List;

public interface IssueRepository {
    void createIssue(Issue issue);

    Issue findIssueById(String issueId);

    List<Issue> getAllIssues();
}