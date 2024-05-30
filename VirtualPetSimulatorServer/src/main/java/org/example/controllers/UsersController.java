package org.example.controllers;

import org.example.Result;
import org.example.entities.User;
import org.example.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/sign-up")
    public Result<User> signUp(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        return usersService.create(username, email, password);
    }

    @GetMapping("/log-in")
    public Result<Integer> logIn(@RequestParam String identifier, @RequestParam String password) {
        Result<User> user = usersService.findByIdentifier(identifier);
        if (user.isSuccess() && user.getData().getPassword().equals(password)) {
            return Result.success(user.getData().getId());
        } else {
            return Result.failure("InvalidCredentials");
        }
    }
}
