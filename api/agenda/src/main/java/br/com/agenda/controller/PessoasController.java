package br.com.agenda.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.dto.request.pessoa.EditarPessoaRequest;
import br.com.agenda.dto.response.pessoa.ConsultarPessoaPorIdResponse;
import br.com.agenda.dto.response.pessoa.ConsultarPessoaResponse;
import br.com.agenda.dto.response.pessoa.CriarPessoaResponse;
import br.com.agenda.dto.response.pessoa.EditarPessoaResponse;
import br.com.agenda.service.pessoa.ConsultarPessoaPorIdService;
import br.com.agenda.service.pessoa.ConsultarPessoaService;
import br.com.agenda.service.pessoa.CriarPessoaService;
import br.com.agenda.dto.request.pessoa.CriarPessoaRequest;
import br.com.agenda.service.pessoa.EditarPessoaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pessoas")
public class PessoasController {

    private final CriarPessoaService criarPessoaService;
    private final ConsultarPessoaService consultarPessoaService;
    private final ConsultarPessoaPorIdService consultarPessoaPorIdService;
    private final EditarPessoaService editarPessoaService;

    @PostMapping("/criar")
    public CriarPessoaResponse criar(@Valid @RequestBody final CriarPessoaRequest request){
        return criarPessoaService.criar(request);
    }

    @GetMapping("/consultar")
    public Page<ConsultarPessoaResponse> consultar(final Pageable pageable){
        return consultarPessoaService.consultar(pageable);
    }

    @GetMapping("/consultar/{pessoaId}")
    public ConsultarPessoaPorIdResponse consultar(@PathVariable final Long pessoaId){
        return consultarPessoaPorIdService.consultar(pessoaId);
    }

    @PutMapping("/editar/{pessoaId}")
    public EditarPessoaResponse editar(
        @Valid @RequestBody final EditarPessoaRequest editarPessoaRequest,
        @PathVariable final Long pessoaId){

        return editarPessoaService.editar(editarPessoaRequest, pessoaId);
    }
}
