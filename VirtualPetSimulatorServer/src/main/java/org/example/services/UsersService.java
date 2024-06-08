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
            return Result.failure("A user with that username or email already exists.");
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
        return Result.failure("We don't know any pet owner with that username or email.");
    }

    public Result<User> findById(int id) {
        User user = usersRepository.findById(id).orElse(null);
        if (user != null) {
            return Result.success(user);
        }
        return Result.failure("We don't know the pet owner you're referring to.");
    }

    public void updateScore(int userId, int score) {
        User user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }
        user.setScore(score);
        usersRepository.save(user);
    }

    public void updateScoreUponLogIn(int userId, int score) {
        User user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }
        user.setScore(user.getScore() + score);
        usersRepository.save(user);
    }

    public void deleteById(int id) {
        usersRepository.deleteById(id);
    }
}
