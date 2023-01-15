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

import br.com.agenda.dto.request.cidade.CriarCidadeRequest;
import br.com.agenda.dto.request.cidade.EditarCidadeRequest;
import br.com.agenda.dto.response.cidade.ApagarCidadeResponse;
import br.com.agenda.dto.response.cidade.ConsultarCidadePorIdResponse;
import br.com.agenda.dto.response.cidade.ConsultarCidadeResponse;
import br.com.agenda.dto.response.cidade.CriarCidadeResponse;
import br.com.agenda.dto.response.cidade.EditarCidadeResponse;
import br.com.agenda.service.cidade.ApagarCidadeService;
import br.com.agenda.service.cidade.ConsultarCidadePorIdService;
import br.com.agenda.service.cidade.ConsultarCidadeService;
import br.com.agenda.service.cidade.CriarCidadeService;
import br.com.agenda.service.cidade.EditarCidadeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cidades")
public class CidadesController {

    private final CriarCidadeService criarCidadeService;
    private final ConsultarCidadeService consultarCidadeService;
    private final ConsultarCidadePorIdService consultarCidadePorIdService;
    private final EditarCidadeService editarCidadeService;
    private final ApagarCidadeService apagarCidadeService;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public CriarCidadeResponse criarCidade(@Valid @RequestBody CriarCidadeRequest request){
        return criarCidadeService.criar(request);
    }

    @GetMapping("/consultar")
    public Page<ConsultarCidadeResponse> consultarCidades(final Pageable pageable){
        return consultarCidadeService.consultar(pageable);
    }

    @GetMapping("/consultar/{cidadeId}")
    public ConsultarCidadePorIdResponse consultarPorId(final @PathVariable Long cidadeId){
        return consultarCidadePorIdService.consultar(cidadeId);
    }

    @PutMapping("/editar/{cidadeId}")
    public EditarCidadeResponse editar(
        final @PathVariable Long cidadeId,
        final @RequestBody EditarCidadeRequest request){

        return editarCidadeService.editar(cidadeId, request);
    }

    @DeleteMapping("/apagar/{cidadeId}")
    public ApagarCidadeResponse apagar(final @PathVariable Long cidadeId){
        return apagarCidadeService.apagar(cidadeId);
    }
}
