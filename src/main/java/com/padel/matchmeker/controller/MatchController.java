package com.padel.matchmeker.controller;

import com.padel.matchmeker.model.Match;
import com.padel.matchmeker.model.ParticipantesDto;
import com.padel.matchmeker.service.MatchService;
import com.padel.matchmeker.service.VotosService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final VotosService votosService;

    @GetMapping("/")
    public String crearMatch(Model model) {
        // Creamos un modelo
        model.addAttribute("participantesDto", new ParticipantesDto());

        // Devuelve la pantalla de inicio.
        return "inicio";
    }

    @PostMapping("/")
    public String recibirParticipantes(
            @ModelAttribute("participantesDto") ParticipantesDto participantesDto,
            @RequestParam("opcion") String opcion
    ){

        if (opcion.equalsIgnoreCase("normal")) {
            // Generar las arrayList de los equipos
            List<String> equipoA = List.of(participantesDto.getParticipanteA1(), participantesDto.getParticipanteA2());
            List<String> equipoB = List.of(participantesDto.getParticipanteB1(), participantesDto.getParticipanteB2());

            // LLamar al método que genera el match
            URI uri = matchService.crearMatch(equipoA, equipoB, participantesDto.getFecha(), participantesDto.getLocalizacion());

            return "redirect:"+uri;
        } else {
            // Generar un arrayList con todos los participantes dentro. y mezclarlos.
            List<String> participantes = new java.util.ArrayList<>(List.of(
                    participantesDto.getParticipanteA1(),
                    participantesDto.getParticipanteA2(),
                    participantesDto.getParticipanteB1(),
                    participantesDto.getParticipanteB2()));

            Collections.shuffle(participantes);

            // Generar los equipos
            List<String> equipoA = List.of(participantes.get(0), participantes.get(1));
            List<String> equipoB = List.of(participantes.get(2), participantes.get(3));

            // LLamar el metodo que genera el match
            URI uri = matchService.crearMatch(equipoA, equipoB, participantesDto.getFecha(), participantesDto.getLocalizacion());

            // redireccionar al endpoint correcto.
            return "redirect:"+uri;
        }


    }

    @GetMapping("/redirect")
    public String redirectMatch(@RequestParam("searchId") Long id) {
        // recibimos el dato puesto en la caja de texto.
        Long matchId = id;


        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        // creamos
        return "redirect:"+uri;
    }

    @GetMapping("/{id}")
    public String getMatch(@PathVariable("id") Long id, Model model, HttpSession session) {
        Boolean haVotado = (Boolean) session.getAttribute("voto");

        // Obtener el match por el id
        Match match = matchService.getMatchById(id);

        // Pasamos el match al modelo
        model.addAttribute("match", match);
        model.addAttribute("fecha", matchService.formatFecha(match.getFecha()));

        // Pasamos los votos al modelo
        model.addAttribute("votos", votosService.obtenerVotos(id));
        model.addAttribute("haVotado", haVotado != null && haVotado );
        // devolvemos la página html que queremos mostrar
        return "match";
    }

    @PostMapping("/votar/{id}")
    public String votarEquipoA(@PathVariable("id") Long id, @RequestParam("btn") String equipo, HttpSession session){

        votosService.aumentarVotos(equipo, id);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        session.setAttribute("voto", true);

        return "redirect:"+uri;
    }

    /*

        Mostrar claramente el id del partido una vez se crea el partido (match)

     */
}
