package com.padel.matchmeker.service;

import com.padel.matchmeker.model.Match;
import com.padel.matchmeker.model.Votos;
import com.padel.matchmeker.repository.VotosRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VotosService {

    private final VotosRepository votosRepository;

    public void crearVoto(Match match) {
        // crear un voto
        Votos nuevoVoto = Votos.builder()
                .votosEquipoA(0)
                .votosEquipoB(0)
                .match(match)
                .build();

        // Guardar en la bd
        votosRepository.save(nuevoVoto);
    }

    public void aumentarVotos(String equipo, Long matchId) {
        // coger el voto de la bd
        Votos voto = votosRepository.findByMatchId(matchId);

        // condicional para aumentar el voto
        if (equipo.equalsIgnoreCase("equipoA"))
            voto.setVotosEquipoA(voto.getVotosEquipoA()+1);

        if (equipo.equalsIgnoreCase("equipoB"))
            voto.setVotosEquipoB(voto.getVotosEquipoB()+1);

        // volver a guardar el voto en la bd
        votosRepository.save(voto);
    }

    public List<Integer> obtenerVotos(Long matchId) {
        // Obtener votos de la bd
        Votos voto = votosRepository.findByMatchId(matchId);

        // Obtener el número de votos y retornarlos
        return List.of(voto.getVotosEquipoA(), voto.getVotosEquipoB());


    }

}
