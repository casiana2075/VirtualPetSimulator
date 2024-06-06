package org.example.entities;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @EqualsAndHashCode
public class User {
    private Integer id;

    private String username;

    private String email;

    private String password;

    private Integer score;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.score = 0;
    }
}
