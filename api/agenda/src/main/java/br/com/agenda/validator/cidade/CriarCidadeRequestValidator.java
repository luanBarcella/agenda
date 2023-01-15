package br.com.agenda.validator.cidade;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.CIDADE_JA_REGISTRADA;
import static br.com.agenda.domain.Message.REQUEST_NAO_INFORMADA;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import br.com.agenda.dto.request.cidade.CriarCidadeRequest;
import br.com.agenda.service.support.ConfigService;
import br.com.agenda.service.support.ConfigService.Key;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.repository.CidadeRepository;
import br.com.agenda.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CriarCidadeRequestValidator implements Consumer<CriarCidadeRequest> {

    private final CidadeRepository cidadeRepository;
    private final MessageService messageService;
    private final ConfigService configService;

    @Override
    public void accept(final CriarCidadeRequest criarCidadeRequest) {
        if(isNull(criarCidadeRequest)){
            log.info("Requisição não informada: {}", criarCidadeRequest);
            throw new ClientErrorException(VALIDATION, messageService.get(REQUEST_NAO_INFORMADA));
        }

        if(cidadeRepository.existsByNomeAndUFAndLinkImagem(
            criarCidadeRequest.getNome(),
            criarCidadeRequest.getUF(),
            ofNullable(criarCidadeRequest.getLinkImagem())
                .orElseGet(() -> configService.get(Key.URL_PADRAO_CIDADES)))){

            log.info("Cidade já registrada: {}", criarCidadeRequest);
            throw new ClientErrorException(VALIDATION, messageService.get(CIDADE_JA_REGISTRADA));
        }
    }
}
