package br.com.agenda.validator.evento;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.EVENTO_JA_REGISTRADO;
import static br.com.agenda.domain.Message.INTERVALO_ENTRE_DATAS_INVALIDO;
import static br.com.agenda.domain.Message.REQUEST_NAO_INFORMADA;
import static br.com.agenda.service.support.ConfigService.Key.URL_PADRAO_EVENTOS;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import br.com.agenda.domain.ErrorType;
import br.com.agenda.dto.request.evento.CriarEventoRequest;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.repository.EventoRepository;
import br.com.agenda.service.support.ConfigService;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.service.support.PegarLocalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CriarEventoRequestValidator implements Consumer<CriarEventoRequest> {

    private final EventoRepository eventoRepository;
    private final PegarLocalService pegarLocalService;
    private final MessageService messageService;
    private final ConfigService configService;

    @Override
    public void accept(final CriarEventoRequest criarEventoRequest) {
        if(isNull(criarEventoRequest)){
            log.info("Requisição não infomada: {}", criarEventoRequest);
            throw new ClientErrorException(VALIDATION, messageService.get(REQUEST_NAO_INFORMADA));
        }

        if(criarEventoRequest.getDataInicio().isAfter(criarEventoRequest.getDataFim())){
            throw new ClientErrorException(ErrorType.VALIDATION, messageService.get(INTERVALO_ENTRE_DATAS_INVALIDO));
        }

        final LocalEntity localEvento = pegarLocalService.porId(criarEventoRequest.getIdLocal());

        if(eventoRepository.existsByTituloAndDataInicioAndDataFimAndLinkImagemAndCategoriaAndLocalEvento(
            criarEventoRequest.getTitulo(),
            criarEventoRequest.getDataInicio(),
            criarEventoRequest.getDataFim(),
            ofNullable(criarEventoRequest.getLinkImagem())
                .orElseGet(() -> configService.get(URL_PADRAO_EVENTOS)),
            criarEventoRequest.getCategoria(),
            localEvento
        )){

            log.info("Evento já registrado: {}", criarEventoRequest);
            throw new ClientErrorException(VALIDATION, messageService.get(EVENTO_JA_REGISTRADO));
        }
    }
}
