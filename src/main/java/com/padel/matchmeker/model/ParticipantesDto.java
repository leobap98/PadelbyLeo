package com.padel.matchmeker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParticipantesDto {
    private String participanteA1, participanteA2, participanteB1, participanteB2;
    private String localizacion;
    private LocalDateTime fecha;

}
