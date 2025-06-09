package service;

import enums.IssueStatus;
import enums.IssueType;
import exceptions.IssueNotFoundException;
import model.Issue;

public interface IssueService {
    Issue createIssue(String transactionId, IssueType type, String subject, String description, String email);

    void getIssuesByEmail(String email);

    void getIssuesByIssueType(IssueType issueType);

    void updateIssue(String id, IssueStatus status, String resolution) throws IssueNotFoundException;

    void resolveIssue(String id, String resolution) throws IssueNotFoundException;
}