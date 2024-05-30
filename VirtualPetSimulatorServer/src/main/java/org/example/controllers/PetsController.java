package org.example.controllers;

import org.example.Result;
import org.example.entities.Pet;
import org.example.services.PetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pets")
public class PetsController {
    @Autowired
    private PetsService petsService;

    @PostMapping("/create")
    public Pet create(@RequestParam int ownerId) {
        return petsService.create(ownerId);
    }

    @GetMapping("/of-owner")
    public Result<Pet> findByOwnerId(@RequestParam int ownerId) {
        Result<Pet> pet = petsService.findByOwnerId(ownerId);
        if (pet.isSuccess()) {
            return Result.success(pet.getData());
        } else {
            return Result.failure(pet.getError());
        }
    }

    @PatchMapping("/rename")
    public Result<String> changeName(@RequestParam int petId, @RequestParam String newName) {
        Result<String> name = petsService.changeName(petId, newName);
        if (name.isSuccess()) {
            return Result.success(name.getData());
        } else {
            return Result.failure(name.getError());
        }
    }

    @PatchMapping("/save")
    public Result<Pet> save(@RequestParam int petId,
                            @RequestParam int currentHunger,
                            @RequestParam int currentHappiness,
                            @RequestParam int currentCleanness) {
        return petsService.save(petId, currentHunger, currentHappiness, currentCleanness);
    }
}
