package strategy;

import model.Issue;

public interface IssueFilterStrategy {
    boolean filter(Issue issue);
}
