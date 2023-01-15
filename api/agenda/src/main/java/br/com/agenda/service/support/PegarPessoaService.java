package br.com.agenda.service.support;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.PESSOA_ID_INVALIDO;

import org.springframework.stereotype.Service;

import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.model.PessoaEntity;
import br.com.agenda.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PegarPessoaService {

    private final PessoaRepository pessoaRepository;
    private final MessageService messageService;

    public PessoaEntity porId(final Long idPessoa){
        return pessoaRepository.findById(idPessoa)
            .orElseThrow(() -> new ClientErrorException(
                VALIDATION,
                messageService.get(PESSOA_ID_INVALIDO)));
    }

}
