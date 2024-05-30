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
    public @ResponseBody Pet create(@RequestParam int ownerId) {
        return petsService.create(ownerId);
    }

    @GetMapping("/of-owner")
    public @ResponseBody Result<Pet> findByOwnerId(@RequestParam int ownerId) {
        return petsService.findByOwnerId(ownerId);
    }

    @PatchMapping("/rename")
    public @ResponseBody Result<String> changeName(@RequestParam int petId, @RequestParam String newName) {
        return petsService.changeName(petId, newName);
    }

    @PatchMapping("/save")
    public @ResponseBody Result<Pet> save(@RequestParam int petId,
                            @RequestParam int currentHunger,
                            @RequestParam int currentHappiness,
                            @RequestParam int currentCleanness) {
        return petsService.save(petId, currentHunger, currentHappiness, currentCleanness);
    }
}
