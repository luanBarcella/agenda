package br.com.agenda.validator.pessoa;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.PESSOA_JA_REGISTRADA;
import static br.com.agenda.domain.Message.REQUEST_NAO_INFORMADA;

import java.util.Objects;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import br.com.agenda.dto.request.pessoa.EditarPessoaRequest;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.repository.PessoaRepository;
import br.com.agenda.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EditarPessoaRequestValidator implements Consumer<EditarPessoaRequest> {

    private final MessageService messageService;
    private final PessoaRepository pessoaRepository;

    @Override
    public void accept(final EditarPessoaRequest request) {
        if(Objects.isNull(request)){
            log.info("Requisição não informada: {}", request);
            throw new ClientErrorException(VALIDATION, messageService.get(REQUEST_NAO_INFORMADA));
        }

        if(pessoaRepository.existsByEmail(request.getEmail())){
            log.info("Pessoa com e-mail {} já consta em nossos registros", request.getEmail());
            throw new ClientErrorException(VALIDATION, messageService.get(PESSOA_JA_REGISTRADA));
        }
    }
}
