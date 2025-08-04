package com.padel.matchmeker.service;

import com.padel.matchmeker.model.Match;
import com.padel.matchmeker.repository.MatchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchService {

    private final MatchRepository matchRepository;
    private final VotosService votosService;

    public URI crearMatch(
            List<String> equipoA,
            List<String> equipoB,
            LocalDateTime fecha,
            String localizacion
    ) {
        Match nuevoMatch = Match.builder()
                .equipoA(equipoA)
                .equipoB(equipoB)
                .fecha(fecha)
                .localizacion(localizacion)
                .build();

        Match matchGuardado = matchRepository.save(nuevoMatch);

        votosService.crearVoto(matchGuardado);

        return generarUri(matchGuardado);
    }

    private URI generarUri(Match matchGuardado) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(matchGuardado.getId())
                .toUri();
    }

    public Match getMatchById(Long id) {
        return matchRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No se ha podido encontrar el Match con el id proporcionado.")
        );
    }

    public String formatFecha(LocalDateTime fecha) {
        int[] componentes = {fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear(), fecha.getHour(), fecha.getMinute()};

        String dia = "";
        String mes = "";

        if (componentes[0] < 10)
            dia = "0"+componentes[0];
        else
            dia = componentes[0] + "";

        if (componentes[1] < 10)
            mes = "0"+componentes[1];
        else
            mes = componentes[1] + "";

        return String.format("%s/%s/%d - %d:%d", dia,mes,componentes[2],componentes[3],componentes[4]);
    }







}
