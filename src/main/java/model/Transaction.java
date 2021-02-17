package model;

import java.util.Date;

public class Transaction {

    String payerName;
    int points;
    Date transactionDate;
    int userId;

    public Transaction(String payerName, int points, Date transactionDate, int userId) {

        this.payerName = payerName;
        this.points = points;
        this.transactionDate = new Date();
        this.userId = userId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "pairName='" + payerName + '\'' +
                ", points=" + points +
                ", transactionDate=" + transactionDate +
                ", userId=" + userId +
                '}';
    }
}
