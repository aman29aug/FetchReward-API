package controller;

import Exception.InsufficientBalanceException;
import Exception.UserIdNotFound;
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
        user.setTotalPoints(user.getTotalPoints() + transaction.getPoints());


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
        if (userRepository.getUsers().get(userId) != null) {
            Queue<Transaction> transactions = userRepository.getUsers().get(userId).getTransactions();
            Map<String, Pair<Integer, Integer>> pointsOldCurrentValue = userRepository.getUsers().get(userId).getPointsOldCurrentValue();


            if (userRepository.getUsers().get(userId).getTotalPoints() < points) {
                throw new InsufficientBalanceException("Total points are less than points to be deducted");
            }

            Iterator<Transaction> iterator = transactions.iterator();
            while (iterator.hasNext()) {
                Transaction transaction = iterator.next();
                int currentValue = transaction.getPoints();


                if (currentValue < 0) {
                    points += Math.abs(currentValue);
                    System.out.println("When 0 is greater" + points);
                    pointsOldCurrentValue.get(transaction.getPayerName()).setCurrentValue(Math.abs(currentValue) + pointsOldCurrentValue.get(transaction.getPayerName()).getCurrentValue());
                } else {
                    if (currentValue <= points) {
                        int updatedValue = pointsOldCurrentValue.get(transaction.getPayerName()).getCurrentValue() - currentValue;
                        pointsOldCurrentValue.get(transaction.getPayerName()).setCurrentValue(updatedValue);
                        points -= (currentValue);
                        System.out.println("When  greater" + currentValue);
                    } else {
                        pointsOldCurrentValue.get(transaction.getPayerName()).setCurrentValue(currentValue - points);
                        System.out.println("When " + currentValue);
                        break;
                    }
                }
            }
            List<String> deductedOutput = new ArrayList<>();
            for (Map.Entry<String, Pair<Integer, Integer>> entry : pointsOldCurrentValue.entrySet()) {
                StringBuilder output = new StringBuilder();
                output.append("[");
                output.append(entry.getKey());
                output.append(", ");
                output.append(entry.getValue().getCurrentValue() - entry.getValue().getOldValue());
                System.out.println(entry.getValue().getOldValue());
                output.append(", ");
                output.append("now");
                output.append("]");

                deductedOutput.add(output.toString());
            }

            return deductedOutput;


        } else {
            throw new UserIdNotFound("The User not in the system");
        }




    }


    @GetMapping("/deduct/{id}")
    public List<String> pointBalance(@PathVariable(name = "id") int userId) {

        if (userRepository.getUsers().get(userId) != null) {
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
        } else {
            throw new UserIdNotFound("The User not in the system");
        }

    }


}
