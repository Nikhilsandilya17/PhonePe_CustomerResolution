package service.impl;

import enums.IssueStatus;
import enums.IssueType;
import exceptions.IssueNotFoundException;
import model.Agent;
import model.Issue;
import model.Transaction;
import repository.IssueRepository;
import repository.impl.IssueRepositoryImpl;
import service.IssueService;
import strategy.IssueFilterStrategy;
import strategy.impl.EmailIssueFilterStrategyImpl;
import strategy.impl.TypeIssueFilterStrategyImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static constants.ApplicationConstants.ASSIGNING_NEXT_WAITING_ISSUE;
import static constants.ApplicationConstants.GETTING_ISSUES_BY_EMAIL;
import static constants.ApplicationConstants.GETTING_ISSUES_BY_ISSUE_TYPE;
import static constants.ApplicationConstants.ISSUE_NOT_FOUND;
import static constants.ApplicationConstants.UPDATING_ISSUE_STATUS_TO;

public class IssueServiceImpl implements IssueService {
    private static IssueService instance;
    private final IssueRepository issueRepository;

    public IssueServiceImpl() {
        issueRepository = new IssueRepositoryImpl();
    }

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
        Transaction transaction = createTransaction(transactionId);
        Issue issue = new Issue(generateIssueId(), transaction, email, type, subject, description);
        issueRepository.createIssue(issue);
        return issue;
    }

    private String generateIssueId() {
        return UUID.randomUUID().toString();
    }

    private Transaction createTransaction(String transactionId) {
        return new Transaction(transactionId);
    }

    @Override
    public void getIssuesByEmail(String email) {
        System.out.println(GETTING_ISSUES_BY_EMAIL + email);
        List<Issue> filteredIssues = filterIssues(new EmailIssueFilterStrategyImpl(email));
        printIssues(filteredIssues);
    }

    @Override
    public void getIssuesByIssueType(IssueType issueType) {
        System.out.println(GETTING_ISSUES_BY_ISSUE_TYPE + issueType);
        List<Issue> filteredIssues = filterIssues(new TypeIssueFilterStrategyImpl(issueType));
        printIssues(filteredIssues);
    }

    private List<Issue> filterIssues(IssueFilterStrategy issueFilterStrategy) {
        List<Issue> filteredIssues = new ArrayList<>();
        for (Issue issue : issueRepository.getAllIssues()) {
            if (issueFilterStrategy.filter(issue)) {
                filteredIssues.add(issue);
            }
        }
        return filteredIssues;
    }

    @Override
    public void updateIssue(String id, IssueStatus status, String resolution) throws IssueNotFoundException {
        Issue issue = getIssue(id);
        if (issue != null) {
            System.out.println(UPDATING_ISSUE_STATUS_TO + status);
            updateIssueStatusAndResolution(status, resolution, issue);
            System.out.println(issue.toString());
            return;
        }
        throw new IssueNotFoundException(ISSUE_NOT_FOUND + id);
    }

    private void updateIssueStatusAndResolution(IssueStatus status, String resolution, Issue issue) {
        issue.setStatus(status);
        issue.setResolution(resolution);
    }

    private Issue getIssue(String id) {
        return issueRepository.findIssueById(id);
    }

    @Override
    public void resolveIssue(String issueId, String resolution) throws IssueNotFoundException {
        Issue issue = getIssue(issueId);
        if (issue == null) {
            throw new IssueNotFoundException(ISSUE_NOT_FOUND + issueId);
        }
        updateIssueStatusAndResolution(IssueStatus.RESOLVED, resolution, issue);
        System.out.println("Issue: " + issue.getIssueId() + " marked " + issue.getStatus());
        updateAgentAssignedIssues(issue.getAssignedAgent(), issue);
    }

    private void updateAgentAssignedIssues(Agent agent, Issue issue) {
        if (agent != null) {
            agent.getAssignedIssues().remove(issue);
            agent.getResolvedIssues().add(issue);
            issue.setAssignedAgent(null);
            assignTheNextWaitingIssue(agent);
        }
    }

    private void assignTheNextWaitingIssue(Agent agent) {
        Issue nextIssue = agent.getAssignedIssues().poll();
        if (nextIssue != null) {
            System.out.println(ASSIGNING_NEXT_WAITING_ISSUE + agent.getName());
            nextIssue.setAssignedAgent(agent);
            nextIssue.setStatus(IssueStatus.ASSIGNED);
            System.out.println("Issue: " + nextIssue.getIssueId() + " assigned to agent: " + agent.getName());
        }
    }

}