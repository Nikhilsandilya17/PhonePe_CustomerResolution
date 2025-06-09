package service;

import enums.IssueStatus;
import enums.IssueType;
import model.Issue;

import java.util.List;

public interface IssueService {
    Issue createIssue(String transactionId, IssueType type, String subject, String description, String email);
    List<Issue> getIssuesByEmail(String email);
    List<Issue> getIssuesByIssueType(IssueType issueType);
    void updateIssue(Issue issue, IssueStatus status, String resolution);
    void resolveIssue(Issue issue, String resolution);
}