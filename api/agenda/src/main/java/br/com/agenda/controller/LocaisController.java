package br.com.agenda.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.dto.response.local.ConsultarLocalPorIdResponse;
import br.com.agenda.dto.response.local.ConsultarLocalResponse;
import br.com.agenda.service.local.ConsultarLocalService;
import br.com.agenda.service.local.CriarLocalService;
import br.com.agenda.dto.request.local.CriarLocalRequest;
import br.com.agenda.dto.request.local.EditarLocalRequest;
import br.com.agenda.dto.response.local.ApagarLocalResponse;
import br.com.agenda.dto.response.local.CriarLocalResponse;
import br.com.agenda.dto.response.local.EditarLocalResponse;
import br.com.agenda.service.local.ApagarLocalService;
import br.com.agenda.service.local.ConsultarLocalPorIdService;
import br.com.agenda.service.local.EditarLocalService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locais")
public class LocaisController {

    private final CriarLocalService criarLocalService;
    private final ConsultarLocalService consultarLocalService;
    private final ConsultarLocalPorIdService consultarLocalPorIdService;
    private final EditarLocalService editarLocalService;
    private final ApagarLocalService apagarLocalService;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public CriarLocalResponse criar(@Valid @RequestBody CriarLocalRequest request){
        return criarLocalService.criar(request);
    }

    @GetMapping("/consultar")
    public Page<ConsultarLocalResponse> consultarLocais(final Pageable pageable){
        return consultarLocalService.consultar(pageable);
    }

    @GetMapping("/consultar/{localId}")
    public ConsultarLocalPorIdResponse consultarPorId(@PathVariable final Long localId){
        return consultarLocalPorIdService.consultar(localId);
    }

    @PutMapping("/editar/{localId}")
    public EditarLocalResponse editarLocal(
        @PathVariable final Long localId,
        @RequestBody final EditarLocalRequest request){

        return editarLocalService.editar(request, localId);
    }

    @DeleteMapping("/apagar/{localId}")
    public ApagarLocalResponse apagar(@PathVariable final Long localId){
        return apagarLocalService.apagar(localId);
    }

}
