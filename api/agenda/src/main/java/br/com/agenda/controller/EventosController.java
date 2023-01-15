package br.com.agenda.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.dto.request.evento.CriarEventoRequest;
import br.com.agenda.dto.response.evento.CriarEventoResponse;
import br.com.agenda.service.evento.CriarEventoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eventos")
public class EventosController {

    private final CriarEventoService criarEventoService;

    @PostMapping("/criar")
    public CriarEventoResponse criar(@Valid @RequestBody final CriarEventoRequest request){
        return criarEventoService.criar(request);
    }

}