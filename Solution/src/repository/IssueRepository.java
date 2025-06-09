package repository;

import enums.IssueType;
import model.Issue;

import java.util.List;

public interface IssueRepository {
    void save(Issue issue);
    Issue findById(String issueId);
    List<Issue> findByEmail(String email);
    List<Issue> findByType(IssueType type);
    List<Issue> findAll();
}