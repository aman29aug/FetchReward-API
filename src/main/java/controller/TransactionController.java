package controller;

import model.Pair;
import model.Transaction;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;

import java.util.*;

@RestController
@RequestMapping("/v1/api")
public class TransactionController {

    @Autowired
    UserRepository userRepository;


    @PostMapping("/transaction")
    public String addTransaction(@RequestBody Transaction transaction) {

        if (!userRepository.getUsers().containsKey(transaction.getUserId())) {
            userRepository.getUsers().put(transaction.getUserId(), new User(transaction.getUserId()));
        }

        User user = userRepository.getUsers().get(transaction.getUserId());

        user.getTransactions().add(transaction);

        if (!user.getPointsOldCurrentValue().containsKey(transaction.getPayerName())) {
            user.getPointsOldCurrentValue().put(transaction.getPayerName(), new Pair<>(0, 0));
        }
        int updatedPoints = user.getPointsOldCurrentValue().get(transaction.getPayerName()).getCurrentValue() + transaction.getPoints();
        user.getPointsOldCurrentValue().get(transaction.getPayerName()).setOldValue(updatedPoints);
        user.getPointsOldCurrentValue().get(transaction.getPayerName()).setCurrentValue(updatedPoints);
        System.out.println(transaction);
        return "Transaction Added Successfully";
    }

    @GetMapping("/deduct/{id}/{points}")
    public List<String> deductPoints(@PathVariable(name = "id") int userId, @PathVariable(name = "points") int points) {
        Queue<Transaction> transactions = userRepository.getUsers().get(userId).getTransactions();
        Map<String, Pair<Integer, Integer>> pointsOldCurrentValue = userRepository.getUsers().get(userId).getPointsOldCurrentValue();

        Iterator<Transaction> iterator = transactions.iterator();
        while (iterator.hasNext()) {
            Transaction transaction = iterator.next();
            int currentValue = transaction.getPoints();

            if (currentValue < 0) {
                points += (currentValue);
                pointsOldCurrentValue.get(transaction.getPayerName()).setCurrentValue((currentValue) + pointsOldCurrentValue.get(transaction.getPayerName()).getCurrentValue());
            } else {
                if (currentValue <= points) {
                    pointsOldCurrentValue.get(transaction.getPayerName()).setCurrentValue(0);
                } else {
                    pointsOldCurrentValue.get(transaction.getPayerName()).setCurrentValue(currentValue - points);
                    break;
                }
            }
        }
        ;


        List<String> deductedOutput = new ArrayList<>();
        for (Map.Entry<String, Pair<Integer, Integer>> entry : pointsOldCurrentValue.entrySet()) {
            StringBuilder output = new StringBuilder();
            output.append("[");
            output.append(entry.getKey());
            output.append(", ");
            output.append(entry.getValue().getCurrentValue() - entry.getValue().getOldValue());
            output.append(", ");
            output.append("now");
            output.append("]");

            deductedOutput.add(output.toString());
        }

        return deductedOutput;
    }


    @GetMapping("/deduct/{id}")
    public List<String> pointBalance(@PathVariable(name = "id") int userId) {
        Map<String, Pair<Integer, Integer>> pointsOldCurrentValue = userRepository.getUsers().get(userId).getPointsOldCurrentValue();
        List<String> deductedOutput = new ArrayList<>();
        for (Map.Entry<String, Pair<Integer, Integer>> entry : pointsOldCurrentValue.entrySet()) {
            StringBuilder output = new StringBuilder();
            output.append("[");
            output.append(entry.getKey());
            output.append(", ");
            output.append(entry.getValue().getCurrentValue());
            deductedOutput.add(output.toString());
        }
        return deductedOutput;


    }


}
