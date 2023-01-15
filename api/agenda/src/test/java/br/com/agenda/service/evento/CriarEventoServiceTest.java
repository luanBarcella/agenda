package br.com.agenda.service.evento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.dto.request.evento.CriarEventoRequest;
import br.com.agenda.dto.response.evento.CriarEventoResponse;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.EventoEntity;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.service.support.PegarLocalService;
import br.com.agenda.validator.evento.CriarEventoRequestValidator;
import br.com.agenda.mapper.evento.CriarEventoRequestMapper;
import br.com.agenda.repository.EventoRepository;

@RunWith(MockitoJUnitRunner.class)
public class CriarEventoServiceTest {

    @InjectMocks
    private CriarEventoService criarEventoService;

    @Mock
    private PegarLocalService pegarLocalService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private CriarEventoRequestMapper criarEventoRequestMapper;

    @Mock
    private CriarEventoRequestValidator criarEventoRequestValidator;

    @Captor
    private ArgumentCaptor<EventoEntity> eventoEntityArgumentCaptor;

    @Test
    @DisplayName("Deve Criar Um Evento Corretamente")
    public void DeveCriarUmEventoCorretamente(){
        final CriarEventoRequest request = Fixture.make(CriarEventoRequest.builder())
            .dataInicio(OffsetDateTime.now())
            .dataFim(OffsetDateTime.now().plusDays(1))
            .build();

        final LocalEntity local = Fixture.make(LocalEntity.builder())
            .build();

        final EventoEntity evento = Fixture.make(EventoEntity.builder())
                .build();

        when(pegarLocalService.porId(request.getIdLocal())).thenReturn(local);
        when(criarEventoRequestMapper.apply(request, local)).thenReturn(evento);

        final CriarEventoResponse response = criarEventoService.criar(request);

        verify(criarEventoRequestValidator).accept(request);
        verify(pegarLocalService).porId(request.getIdLocal());
        verify(criarEventoRequestMapper).apply(request, local);
        verify(eventoRepository).save(eventoEntityArgumentCaptor.capture());

        assertNotNull(response);
        assertEquals(eventoEntityArgumentCaptor.getValue().getId(), response.getEventoId());
    }
}
