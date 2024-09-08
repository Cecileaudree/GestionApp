package com.example.projetA.Entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 45)
    private String nom;
}
