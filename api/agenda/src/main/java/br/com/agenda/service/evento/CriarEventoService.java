package br.com.agenda.service.evento;

import org.springframework.stereotype.Service;

import br.com.agenda.dto.request.evento.CriarEventoRequest;
import br.com.agenda.dto.response.evento.CriarEventoResponse;
import br.com.agenda.mapper.evento.CriarEventoRequestMapper;
import br.com.agenda.model.EventoEntity;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.repository.EventoRepository;
import br.com.agenda.service.support.PegarLocalService;
import br.com.agenda.validator.evento.CriarEventoRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriarEventoService {

    public final CriarEventoRequestMapper criarEventoRequestMapper;
    private final PegarLocalService pegarLocalService;
    private final EventoRepository eventoRepository;
    private final CriarEventoRequestValidator criarEventoRequestValidator;

    public CriarEventoResponse criar(final CriarEventoRequest request){
        log.info("Iniciando processo de criação de evento para: {}", request);
        criarEventoRequestValidator.accept(request);

        final LocalEntity local = pegarLocalService.porId(request.getIdLocal());

        final EventoEntity evento = criarEventoRequestMapper.apply(request, local);

        eventoRepository.save(evento);

        return CriarEventoResponse.builder()
            .eventoId(evento.getId())
            .build();
    }

}
