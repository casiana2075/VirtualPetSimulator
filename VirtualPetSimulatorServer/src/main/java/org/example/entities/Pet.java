package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pets")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @EqualsAndHashCode
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ownerId", nullable = false)
    private Integer ownerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastUpdate", nullable = false)
    private LocalDateTime lastUpdate;

    @Column(name = "hunger", nullable = false)
    private Integer hunger;

    @Column(name = "happiness", nullable = false)
    private Integer happiness;

    @Column(name = "cleanness", nullable = false)
    private Integer cleanness;

    public Pet(Integer ownerId) {
        this.ownerId = ownerId;
        this.name = "Your pet";
        this.lastUpdate = LocalDateTime.now();
        this.hunger = 50;
        this.happiness = 50;
        this.cleanness = 50;
    }

    public int applyStatsDecrease(Integer[] statsDifference) {
        int scoreDifference = 0;
        hunger -= statsDifference[0];
        if (hunger < 0) {
            scoreDifference += hunger;
            hunger = 0;
        }
        happiness -= statsDifference[1];
        if (happiness < 0) {
            scoreDifference += happiness;
            happiness = 0;
        }
        cleanness -= statsDifference[2];
        if (cleanness < 0) {
            scoreDifference += cleanness;
            cleanness = 0;
        }

        return scoreDifference;
    }
}
