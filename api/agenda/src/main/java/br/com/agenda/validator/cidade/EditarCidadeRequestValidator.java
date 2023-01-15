package br.com.agenda.validator.cidade;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.CIDADE_JA_REGISTRADA;
import static br.com.agenda.domain.Message.CIDADE_NAO_INFORMADA;
import static br.com.agenda.domain.Message.REQUEST_NAO_INFORMADA;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Component;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.dto.request.cidade.EditarCidadeRequest;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.repository.CidadeRepository;
import br.com.agenda.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EditarCidadeRequestValidator implements BiConsumer<EditarCidadeRequest, CidadeEntity> {

    private final CidadeRepository cidadeRepository;
    private final MessageService messageService;

    @Override
    public void accept(final EditarCidadeRequest request, final CidadeEntity cidade) {
        if(isNull(request)){
            log.info("Requisição não informada: {}", request);
            throw new ClientErrorException(VALIDATION, messageService.get(REQUEST_NAO_INFORMADA));
        }

        if(isNull(cidade)){
            log.info("Cidade não informada: {}", cidade);
            throw new ClientErrorException(VALIDATION, messageService.get(CIDADE_NAO_INFORMADA));
        }

        if(cidadeRepository.existsByNomeAndUFAndLinkImagem(
            ofNullable(request.getNome()).orElse(cidade.getNome()),
            ofNullable(request.getUF()).orElse(cidade.getUF()),
            ofNullable(request.getLinkImagem()).orElse(cidade.getLinkImagem()))){

            log.info("Cidade já registrada request: {}, cidade: {}", request, cidade);
            throw new ClientErrorException(VALIDATION, messageService.get(CIDADE_JA_REGISTRADA));
        }
    }
}
