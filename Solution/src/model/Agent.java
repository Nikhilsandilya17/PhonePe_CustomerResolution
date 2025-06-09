package model;

import enums.IssueType;

import java.util.ArrayList;
import java.util.List;

public class Agent {

    private String email;
    private String name;
    private List<IssueType> expertise;
    private List<Issue> assignedIssues;
    private List<Issue> resolvedIssues;
    private List<Issue> overallAssignedIssues;

    public Agent(String email, String name, List<IssueType>expertise) {
        this.email = email;
        this.name = name;
        this.expertise = expertise;
        resolvedIssues = new ArrayList<>();
        assignedIssues = new ArrayList<>();
        overallAssignedIssues = new ArrayList<>();
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

    public List<Issue> getAssignedIssues() {
        return assignedIssues;
    }

    public void setAssignedIssues(List<Issue> assignedIssues) {
        this.assignedIssues = assignedIssues;
    }

    public List<Issue> getResolvedIssues() {
        return resolvedIssues;
    }

    public void setResolvedIssues(List<Issue> resolvedIssues) {
        this.resolvedIssues = resolvedIssues;
    }

    public List<Issue> getOverallAssignedIssues() {
        return overallAssignedIssues;
    }

    public void setOverallAssignedIssues(List<Issue> overallAssignedIssues) {
        this.overallAssignedIssues = overallAssignedIssues;
    }
}
