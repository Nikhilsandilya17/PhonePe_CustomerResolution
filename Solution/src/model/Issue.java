package model;

import enums.IssueStatus;
import enums.IssueType;

import java.time.LocalDateTime;

public class Issue {
    private String issueId;
    private Transaction transaction;
    private String customerEmail;
    private String subject;
    private String description;
    private LocalDateTime createdAt;
    private IssueType type;
    private IssueStatus status;
    private Agent assignedAgent;
    private String resolution;

    public Issue(String issueId, Transaction transaction, String customerEmail, IssueType type, String subject, String description) {
        this.issueId = issueId;
        this.transaction = transaction;
        this.customerEmail = customerEmail;
        this.type = type;
        this.subject = subject;
        this.description = description;
        this.status = IssueStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssueType getType() {
        return type;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public Agent getAssignedAgent() {
        return assignedAgent;
    }

    public void setAssignedAgent(Agent assignedAgent) {
        this.assignedAgent = assignedAgent;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return "Issue{" + "issueId='" + issueId + '\'' +
                ", subject='" + subject + '\'' +
                ", type=" + type +
                '}';
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
