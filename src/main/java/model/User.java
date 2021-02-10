package model;


import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class User {


    private int id;
    private int totalPoints;
    private Queue<Transaction> transactions;
    private Map<String, Pair<Integer, Integer>> pointsOldCurrentValue;


    public User(int id) {
        this.id = id;
        this.transactions = new PriorityQueue<>((t1, t2) -> (t1.getTransactionDate().compareTo(t2.getTransactionDate())));
        this.pointsOldCurrentValue = new HashMap<>();
    }

    public Queue<Transaction> getTransactions() {
        return transactions;
    }

    public Map<String, Pair<Integer, Integer>> getPointsOldCurrentValue() {
        return pointsOldCurrentValue;
    }
}
