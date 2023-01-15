package br.com.agenda.mapper.evento;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.function.BiFunction;

import org.springframework.stereotype.Component;

import br.com.agenda.dto.request.evento.CriarEventoRequest;
import br.com.agenda.model.EventoEntity;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.service.support.ConfigService;
import br.com.agenda.service.support.ConfigService.Key;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CriarEventoRequestMapper
    implements BiFunction<CriarEventoRequest, LocalEntity, EventoEntity> {

    private final ConfigService configService;

    @Override
    public EventoEntity apply(final CriarEventoRequest request, final LocalEntity local) {
        return EventoEntity.builder()
            .titulo(request.getTitulo())
            .dataInicio(request.getDataInicio())
            .dataFim(request.getDataFim())
            .categoria(request.getCategoria())
            .localEvento(local)
            .linkImagem(ofNullable(request.getLinkImagem())
                .orElseGet(() -> configService.get(Key.URL_PADRAO_EVENTOS)))
            .pessoas(new ArrayList<>())
            .build();
    }
}
