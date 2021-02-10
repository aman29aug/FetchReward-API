package repository;

import model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserRepository {

    HashMap<Integer, User> users = new HashMap<>();

    public HashMap<Integer, User> getUsers() {
        return users;
    }
}
