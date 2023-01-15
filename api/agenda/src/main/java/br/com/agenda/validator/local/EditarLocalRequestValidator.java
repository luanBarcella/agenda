package br.com.agenda.validator.local;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.LOCAL_JA_REGISTRADO;
import static br.com.agenda.domain.Message.LOCAL_NAO_INFORMADO;
import static br.com.agenda.domain.Message.REQUEST_NAO_INFORMADA;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Component;

import br.com.agenda.repository.LocalRepository;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.dto.request.local.EditarLocalRequest;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.model.LocalEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EditarLocalRequestValidator implements BiConsumer<EditarLocalRequest, LocalEntity> {

    private final MessageService messageService;
    private final LocalRepository localRepository;

    @Override
    public void accept(final EditarLocalRequest request, final LocalEntity local) {
        if(isNull(request)){
            log.info("Requisição não informada: {}", request);
            throw new ClientErrorException(VALIDATION, messageService.get(REQUEST_NAO_INFORMADA));
        }

        if(isNull(local)){
            log.info("Local não informado: {}", local);
            throw new ClientErrorException(VALIDATION, messageService.get(LOCAL_NAO_INFORMADO));
        }

        if(localRepository.existsByNomeAndLinkImagem(
            ofNullable(request.getNome()).orElse(local.getNome()),
            ofNullable(request.getLinkImagem()).orElse(local.getLinkImagem())
        )){

            log.info("Local já registrado. request: {}, local: {}", request, local);
            throw new ClientErrorException(VALIDATION, messageService.get(LOCAL_JA_REGISTRADO));
        }
    }
}
