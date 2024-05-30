package org.example.repositories;

import org.example.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetsRepository extends JpaRepository<Pet, Integer> {
    Pet findByOwnerId(int ownerId);
}
