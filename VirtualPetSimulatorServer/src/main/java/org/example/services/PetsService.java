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

    public Pet create(int ownerId, String name) {
        Pet pet = new Pet(ownerId, name);
        return petsRepository.save(pet);
    }

    public Result<Pet> findById(int petId) {
        Pet pet = petsRepository.findById(petId).orElse(null);
        if (pet == null) {
            return Result.failure("PetNotFound");
        }
        return Result.success(pet);
    }

    public Result<Pet> findByOwnerId(int ownerId) {
        Pet pet = petsRepository.findByOwnerId(ownerId);
        if (pet == null) {
            return Result.failure("PetNotFound");
        }
        int scoreDecrease = pet.applyStatsDecrease(getStatsDifference(pet));
        usersService.updateScore(pet.getOwnerId(), -scoreDecrease);
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

    public Result<Integer> save(int petId, int currentHunger, int currentHappiness, int currentCleanness) {
        Result<Pet> petResult = findById(petId);
        if (!petResult.isSuccess()) {
            return Result.failure(petResult.getError());
        }
        Pet pet = petResult.getData();
        int scoreDifference = 0;
        scoreDifference += Math.max(0, currentHunger - pet.getHunger());
        scoreDifference += Math.max(0, currentHappiness - pet.getHappiness());
        scoreDifference += Math.max(0, currentCleanness - pet.getCleanness());

        usersService.updateScore(pet.getOwnerId(), scoreDifference);

        pet.setHunger(currentHunger);
        pet.setHappiness(currentHappiness);
        pet.setCleanness(currentCleanness);
        pet.setLastUpdate(LocalDateTime.now());
        petsRepository.save(pet);

        return Result.success(scoreDifference);
    }
}
