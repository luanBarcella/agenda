package br.com.agenda.service.pessoa;

import org.springframework.stereotype.Component;

import br.com.agenda.dto.request.pessoa.EditarPessoaRequest;
import br.com.agenda.dto.response.pessoa.EditarPessoaResponse;
import br.com.agenda.service.support.PegarPessoaService;
import br.com.agenda.validator.pessoa.EditarPessoaRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EditarPessoaService {

    private final PegarPessoaService pegarPessoaService;
    private final EditarPessoaRequestValidator editarPessoaRequestValidator;

    public EditarPessoaResponse editar(final EditarPessoaRequest request, final Long pessoaId){
        log.info("Iniciando processo de edição da pessoa: {}", pessoaId);


        return null;
    }

}
