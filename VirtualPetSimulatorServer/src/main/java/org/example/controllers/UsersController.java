package org.example.controllers;

import org.example.Result;
import org.example.entities.Pet;
import org.example.entities.User;
import org.example.services.PetsService;
import org.example.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PetsService petsService;

    @PostMapping("/sign-up")
    public @ResponseBody Result<User> signUp(@RequestParam String username,
        @RequestParam String email,
        @RequestParam String password,
        @RequestParam String petName) {
        Result<User> newUser = usersService.create(username, email, password);
        if (newUser.isSuccess()) {
            Result<Pet> pet = petsService.create(newUser.getData().getId(), petName);
            if (pet.isSuccess()) {
                return newUser;
            }
            usersService.deleteById(newUser.getData().getId());
            return Result.failure(pet.getError());
        }
        return Result.failure(newUser.getError());
    }

    @GetMapping("/log-in")
    public @ResponseBody Result<User> logIn(@RequestParam String identifier, @RequestParam String password) {
        Result<User> user = usersService.findByIdentifier(identifier);
        if (user.isSuccess() && user.getData().getPassword().equals(password)) {
            petsService.findByOwnerId(user.getData().getId());
            return user;
        } else {
            return Result.failure("Invalid username or password.");
        }
    }

    @GetMapping("/{id}/score")
    public @ResponseBody Result<Integer> getScore(@PathVariable int id) {
        Result<User> user = usersService.findById(id);
        if (user.isSuccess()) {
            return Result.success(user.getData().getScore());
        }
        return Result.failure("We couldn't find the score you're looking for.");
    }
}
