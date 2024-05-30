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
    public @ResponseBody Result<User> signUp(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        return usersService.create(username, email, password);
    }

    @GetMapping("/log-in")
    public @ResponseBody Result<Integer> logIn(@RequestParam String identifier, @RequestParam String password) {
        Result<User> user = usersService.findByIdentifier(identifier);
        if (user.isSuccess() && user.getData().getPassword().equals(password)) {
            return Result.success(user.getData().getId());
        } else {
            return Result.failure("InvalidCredentials");
        }
    }

    @PostMapping("/adopt-pet")
    public @ResponseBody Result<Pet> adoptPet(@RequestParam int userId) {
        Result<Pet> pet = petsService.findByOwnerId(userId);
        if (pet.isSuccess()) {
            return Result.failure("PetAlreadyAdopted");
        }
        return Result.success(petsService.create(userId));
    }
}
