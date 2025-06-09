package model;

import enums.IssueType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Agent {
    private String email;
    private String name;
    private List<IssueType> expertise;
    private Queue<Issue> assignedIssues;
    private List<Issue> resolvedIssues;
    private List<Issue> assignedIssuesHistory;

    public Agent(String email, String name, List<IssueType>expertise) {
        this.email = email;
        this.name = name;
        this.expertise = expertise;
        resolvedIssues = new ArrayList<>();
        assignedIssues = new LinkedList<>();
        assignedIssuesHistory = new ArrayList<>();
    }

    public boolean isAvailable() {
        return assignedIssues.isEmpty();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IssueType> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<IssueType> expertise) {
        this.expertise = expertise;
    }

    public Queue<Issue> getAssignedIssues() {
        return assignedIssues;
    }

    public void setAssignedIssues(Queue<Issue> assignedIssues) {
        this.assignedIssues = assignedIssues;
    }

    public List<Issue> getResolvedIssues() {
        return resolvedIssues;
    }

    public void setResolvedIssues(List<Issue> resolvedIssues) {
        this.resolvedIssues = resolvedIssues;
    }

    public List<Issue> getAssignedIssuesHistory() {
        return assignedIssuesHistory;
    }

    public void setAssignedIssuesHistory(List<Issue> assignedIssuesHistory) {
        this.assignedIssuesHistory = assignedIssuesHistory;
    }
}
