package strategy.impl;

import model.Issue;
import strategy.IssueFilterStrategy;

public class EmailIssueFilterStrategyImpl implements IssueFilterStrategy {
    private final String email;
    public EmailIssueFilterStrategyImpl(String email) {
        this.email = email;
    }
    @Override
    public boolean filter(Issue issue) {
        return issue.getCustomerEmail().equals(email);
    }
}
