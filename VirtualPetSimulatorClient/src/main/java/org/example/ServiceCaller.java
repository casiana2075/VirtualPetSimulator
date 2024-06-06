package org.example;

import org.example.entities.Pet;
import org.example.entities.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class ServiceCaller {
    private final static String usersUri = "http://localhost:2048/users";
    private final static String petsUri = "http://localhost:2048/pets";

    @PostMapping
    public static Result<User> signUp(String username, String email, String password, String petName) {
        String requestUri = usersUri + "/sign-up?"
                + "username=" + username + "&"
                + "email=" + email + "&"
                + "password=" + password + "&"
                + "petName=" + petName;
        return WebClient.create()
                .post()
                .uri(requestUri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<User>>() {
                })
                .block();
    }

    @GetMapping
    public static Result<User> logIn(String identifier, String password) {
        String requestUri = usersUri + "/log-in?"
                + "identifier=" + identifier + "&"
                + "password=" + password;
        return WebClient.create()
                .get()
                .uri(requestUri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<User>>() {
                })
                .block();
    }

    @GetMapping
    public static Result<Pet> getPet(int ownerId) {
        String requestUri = petsUri + "/of-owner?ownerId=" + ownerId;
        return WebClient.create()
                .get()
                .uri(requestUri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<Pet>>() {
                })
                .block();
    }

    @PatchMapping
    public static Result<Void> savePet(int petId, int currentHunger, int currentHappiness, int currentCleanness) {
        String requestUri = petsUri + "/save?"
                + "petId=" + petId + "&"
                + "currentHunger=" + currentHunger + "&"
                + "currentHappiness=" + currentHappiness + "&"
                + "currentCleanness=" + currentCleanness;
        return WebClient.create()
                .patch()
                .uri(requestUri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<Void>>() {
                })
                .block();
    }

    @PatchMapping
    public static Result<Integer> updatePetStat(int petId, String stat, int value) {
        String requestUri = petsUri + "/update?"
                + "petId=" + petId + "&"
                + "stat=" + stat + "&"
                + "value=" + value;
        return WebClient.create()
                .patch()
                .uri(requestUri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<Integer>>() {
                })
                .block();
    }

    @GetMapping
    public static Result<Integer> getScore(int id) {
        String requestUri = usersUri + "/" + id + "/score";
        return WebClient.create()
                .get()
                .uri(requestUri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<Integer>>() {
                })
                .block();
    }
}
