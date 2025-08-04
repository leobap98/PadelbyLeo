package com.padel.matchmeker.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "votos")
public class Votos {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int votosEquipoA;
    @Column(nullable = false)
    private int votosEquipoB;

    @OneToOne
    @JoinColumn(name = "match_id", unique = true)
    private Match match;
}
