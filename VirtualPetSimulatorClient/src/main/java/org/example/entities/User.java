package org.example.entities;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @EqualsAndHashCode
public final class User {
    private Integer id;

    private String username;

    private String email;

    private String password;

    private Integer score;
}
