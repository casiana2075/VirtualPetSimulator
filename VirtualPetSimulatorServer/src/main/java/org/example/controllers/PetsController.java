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

    @GetMapping("/of-owner")
    public @ResponseBody Result<Pet> findByOwnerId(@RequestParam int ownerId) {
        return petsService.findByOwnerId(ownerId);
    }

    @PatchMapping("/save")
    public @ResponseBody Result<Void> save(@RequestParam int petId,
                            @RequestParam int currentHunger,
                            @RequestParam int currentHappiness,
                            @RequestParam int currentCleanness) {
        return petsService.save(petId, currentHunger, currentHappiness, currentCleanness);
    }

    @PatchMapping("/update")
    public @ResponseBody Result<Integer> update(@RequestParam int petId,
                            @RequestParam String stat,
                            @RequestParam int value) {
        return petsService.update(petId, stat, value);
    }
}
