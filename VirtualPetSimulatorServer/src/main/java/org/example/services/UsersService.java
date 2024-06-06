package org.example.services;

import org.example.Result;
import org.example.entities.User;
import org.example.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public Result<User> create(String username, String email, String password) {
        User user = new User(username, email, password);
        try {
            User newUser = usersRepository.save(user);
            return Result.success(newUser);
        } catch (Exception e) {
            return Result.failure("DuplicateUsernameOrEmail");
        }
    }

    public Result<User> findByIdentifier(String identifier) {
        User user = usersRepository.findByUsername(identifier);
        if (user != null) {
            return Result.success(user);
        }
        user = usersRepository.findByEmail(identifier);
        if (user != null) {
            return Result.success(user);
        }
        return Result.failure("UserNotFound");
    }

    public Result<User> findById(int id) {
        User user = usersRepository.findById(id).orElse(null);
        if (user != null) {
            return Result.success(user);
        }
        return Result.failure("UserNotFound");
    }

    public User updateScore(int userId, int score) {
        User user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        user.setScore(user.getScore() + score);
        return usersRepository.save(user);
    }

    public void deleteById(int id) {
        usersRepository.deleteById(id);
    }
}
