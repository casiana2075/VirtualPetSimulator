package org.example.entities;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @EqualsAndHashCode
public final class Pet {
    private Integer id;

    private Integer ownerId;

    private String name;

    private LocalDateTime lastUpdate;

    private Integer hunger;

    private Integer happiness;

    private Integer cleanness;
}
