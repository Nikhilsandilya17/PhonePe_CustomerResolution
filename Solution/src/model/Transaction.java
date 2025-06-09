package model;

import enums.TransactionStatus;

public class Transaction {
    private String transactionId;
    private TransactionStatus status;

    public Transaction(String transactionId) {
        this.transactionId = transactionId;
        this.status = TransactionStatus.FAILED;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
