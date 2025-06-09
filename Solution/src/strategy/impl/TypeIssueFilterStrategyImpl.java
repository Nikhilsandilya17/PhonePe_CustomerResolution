package strategy.impl;

import enums.IssueType;
import model.Issue;
import strategy.IssueFilterStrategy;

public class TypeIssueFilterStrategyImpl implements IssueFilterStrategy {
    private final IssueType type;

    public TypeIssueFilterStrategyImpl(IssueType type) {
        this.type = type;
    }

    @Override
    public boolean filter(Issue issue) {
        return issue.getType().equals(type);
    }
}
