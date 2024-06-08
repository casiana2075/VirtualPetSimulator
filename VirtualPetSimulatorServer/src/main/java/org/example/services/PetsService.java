package org.example.services;

import org.example.Result;
import org.example.entities.Pet;
import org.example.repositories.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PetsService {
    @Autowired
    private PetsRepository petsRepository;
    @Autowired
    private UsersService usersService;

    public Result<Pet> create(int ownerId, String name) {
        Pet pet = null;
        try {
            pet = petsRepository.save(new Pet(ownerId, name));
        } catch (Exception e) {
            return Result.failure("Something went wrong while getting you your perfect pet. Please try again.");
        }
        return Result.success(pet);
    }

    public Result<Pet> findById(int petId) {
        Pet pet = petsRepository.findById(petId).orElse(null);
        if (pet == null) {
            return Result.failure("Terrible! We couldn't find your pet. Please try again.");
        }
        return Result.success(pet);
    }

    public Result<Pet> findByOwnerId(int ownerId) {
        Pet pet = petsRepository.findByOwnerId(ownerId);
        if (pet == null) {
            return Result.failure("Terrible! We couldn't find your pet. Please try again.");
        }
        Integer[] statsDecrease = getStatsDifference(pet);
        int scoreDecrease = pet.applyStatsDecrease(statsDecrease);
        usersService.updateScoreUponLogIn(pet.getOwnerId(), scoreDecrease);
        updatePetUponLogIn(pet, statsDecrease);
        return Result.success(pet);
    }

    private Integer[] getStatsDifference(Pet pet) {
        LocalDateTime lastUpdate = pet.getLastUpdate();
        LocalDateTime now = LocalDateTime.now();
        int minutesDifference = now.minusMinutes(lastUpdate.getMinute()).getMinute();

        int hungerDifference = minutesDifference / 10;
        int happinessDifference = minutesDifference / 5;
        int cleannessDifference = minutesDifference / 15;

        return new Integer[]{hungerDifference, happinessDifference, cleannessDifference};
    }

    private void updatePetUponLogIn(Pet pet, Integer[] statsDifference) {
        pet.setHunger(Math.max(0, pet.getHunger() - statsDifference[0]));
        pet.setHappiness(Math.max(0, pet.getHappiness() - statsDifference[1]));
        pet.setCleanness(Math.max(0, pet.getCleanness() - statsDifference[2]));
        pet.setLastUpdate(LocalDateTime.now());
        petsRepository.save(pet);
    }

    public Result<Void> save(int petId, int currentHunger, int currentHappiness, int currentCleanness, int currentScore) {
        Pet pet = petsRepository.findById(petId).orElse(null);
        if (pet == null) {
            return Result.failure("Terrible! We couldn't find your pet. Please try again.");
        }
        pet.setHunger(currentHunger);
        pet.setHappiness(currentHappiness);
        pet.setCleanness(currentCleanness);
        pet.setLastUpdate(LocalDateTime.now());
        petsRepository.save(pet);
        usersService.updateScore(pet.getOwnerId(), currentScore);
        return Result.success(null);
    }
}
