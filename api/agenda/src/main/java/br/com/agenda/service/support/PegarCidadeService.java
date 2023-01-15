package br.com.agenda.service.support;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.CIDADE_ID_INVALIDO;

import org.springframework.stereotype.Service;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PegarCidadeService {

    private final CidadeRepository cidadeRepository;
    private final MessageService messageService;

    public CidadeEntity porId(final Long idCidade){
        return cidadeRepository.findById(idCidade)
            .orElseThrow(() -> new ClientErrorException(
                VALIDATION,
                messageService.get(CIDADE_ID_INVALIDO)));
    }
}
