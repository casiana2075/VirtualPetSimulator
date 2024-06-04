package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "score", nullable = false)
    private Integer score;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.score = 0;
    }
}
