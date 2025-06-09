package model;

import enums.TransactionStatus;

public class Transaction {
    String transactionId;
    TransactionStatus status;

    public Transaction(String transactionId, TransactionStatus status) {
        this.transactionId = transactionId;
        this.status = status;
    }

}
