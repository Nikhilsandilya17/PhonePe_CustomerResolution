package exceptions;

public class IssueNotFoundException extends Exception{
    public IssueNotFoundException(String message) {
        super(message);
    }
}
