package br.com.agenda.validator.local;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.LOCAL_JA_REGISTRADO;
import static br.com.agenda.domain.Message.REQUEST_NAO_INFORMADA;
import static br.com.agenda.service.support.ConfigService.Key.URL_PADRAO_LOCAIS;
import static java.util.Objects.isNull;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import br.com.agenda.dto.request.local.CriarLocalRequest;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.repository.LocalRepository;
import br.com.agenda.service.support.ConfigService;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.service.support.PegarCidadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CriarLocalRequestValidator implements Consumer<CriarLocalRequest> {

    private final LocalRepository localRepository;
    private final PegarCidadeService pegarCidadeService;
    private final ConfigService configService;
    private final MessageService messageService;

    @Override
    public void accept(final CriarLocalRequest request) {
        if(isNull(request)){
            log.info("Requisição não informada: {}", request);
            throw new ClientErrorException(VALIDATION, messageService.get(REQUEST_NAO_INFORMADA));
        }

        final CidadeEntity cidade = pegarCidadeService.porId(request.getIdCidade());

        if(localRepository.existsByNomeAndLinkImagemAndCidade(
            request.getNome(),
            Optional.ofNullable(request.getLinkImagem())
                .orElseGet(() -> configService.get(URL_PADRAO_LOCAIS)),
            cidade
        )){

            log.info("Local já registrado: {}", request);
            throw new ClientErrorException(VALIDATION, messageService.get(LOCAL_JA_REGISTRADO));
        }
    }
}
