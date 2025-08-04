package com.padel.matchmeker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "matches")
public class Match {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private List<String> equipoA;
    @Column(nullable = false)
    private List<String> equipoB;
    @Column(nullable = false)
    private LocalDateTime fecha;
    @Column(nullable = false)
    private String localizacion;


}
