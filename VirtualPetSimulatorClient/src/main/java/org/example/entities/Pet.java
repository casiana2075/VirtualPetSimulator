package org.example.entities;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @EqualsAndHashCode
public class Pet {
    private Integer id;

    private Integer ownerId;

    private String name;

    private LocalDateTime lastUpdate;

    private Integer hunger;

    private Integer happiness;

    private Integer cleanness;

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
