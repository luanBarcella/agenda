package br.com.agenda.validator.evento;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.EVENTO_JA_REGISTRADO;
import static br.com.agenda.domain.Message.INTERVALO_ENTRE_DATAS_INVALIDO;
import static br.com.agenda.domain.Message.REQUEST_NAO_INFORMADA;
import static br.com.agenda.service.support.ConfigService.Key.URL_PADRAO_EVENTOS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.dto.request.evento.CriarEventoRequest;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.repository.EventoRepository;
import br.com.agenda.service.support.ConfigService;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.service.support.PegarLocalService;

@RunWith(MockitoJUnitRunner.class)
public class CriarEventoRequestValidatorTest {

    public static final String LINK_IMAGEM_PADRAO_EVENTOS = "https://images.unsplash.com/photo-1561912774-79769a0a0a7a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fGV2ZW50c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60";

    @InjectMocks
    private CriarEventoRequestValidator criarEventoRequestValidator;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private PegarLocalService pegarLocalService;

    @Mock
    private MessageService messageService;

    @Mock
    private ConfigService configService;

    @Test
    @DisplayName("Deve Lançar ClientErrorException Quando Request For Null")
    public void DeveLancarClientErrorExceptionQuandoRequestForNull(){
        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
           criarEventoRequestValidator.accept(null);
        });

        verify(messageService).get(REQUEST_NAO_INFORMADA);

        verifyZeroInteractions(configService);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Deve Retornar ClientErrorException Quando DataInicio For Depois De DataFim")
    public void DeveRetornarClientErrorExceptionQuandoDataInicioForDepoisDeDataFim(){
        final CriarEventoRequest request = Fixture.make(CriarEventoRequest.builder())
            .dataInicio(OffsetDateTime.now())
            .dataFim(OffsetDateTime.now().minusHours(2L))
            .build();

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            criarEventoRequestValidator.accept(request);
        });

        verify(messageService).get(INTERVALO_ENTRE_DATAS_INVALIDO);

        verifyZeroInteractions(configService);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Deve Lançar ClientErrorException Quando Evento Já Existir")
    public void DeveLancarClientErrorExceptionQuandoEventoJaExistir(){
        final CriarEventoRequest request = Fixture.make(CriarEventoRequest.builder())
            .dataFim(OffsetDateTime.now().plusDays(2L))
            .dataInicio(OffsetDateTime.now())
            .build();

        final LocalEntity local = Fixture.make(LocalEntity.builder())
            .build();

        when(pegarLocalService.porId(request.getIdLocal())).thenReturn(local);
        when(eventoRepository.existsByTituloAndDataInicioAndDataFimAndLinkImagemAndCategoriaAndLocalEvento(
            request.getTitulo(),
            request.getDataInicio(),
            request.getDataFim(),
            request.getLinkImagem(),
            request.getCategoria(),
            local
        )).thenReturn(true);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            criarEventoRequestValidator.accept(request);
        });

        verify(pegarLocalService).porId(request.getIdLocal());
        verify(eventoRepository).existsByTituloAndDataInicioAndDataFimAndLinkImagemAndCategoriaAndLocalEvento(
            request.getTitulo(),
            request.getDataInicio(),
            request.getDataFim(),
            request.getLinkImagem(),
            request.getCategoria(),
            local);
        verify(messageService).get(EVENTO_JA_REGISTRADO);

        verifyZeroInteractions(configService);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Sucesso")
    public void sucesso(){
        final CriarEventoRequest request = Fixture.make(CriarEventoRequest.builder())
            .dataFim(OffsetDateTime.now().plusDays(2L))
            .dataInicio(OffsetDateTime.now())
            .linkImagem(null)
            .build();

        final LocalEntity local = Fixture.make(LocalEntity.builder())
            .build();

        when(pegarLocalService.porId(request.getIdLocal())).thenReturn(local);
        when(eventoRepository.existsByTituloAndDataInicioAndDataFimAndLinkImagemAndCategoriaAndLocalEvento(
            request.getTitulo(),
            request.getDataInicio(),
            request.getDataFim(),
            LINK_IMAGEM_PADRAO_EVENTOS,
            request.getCategoria(),
            local
        )).thenReturn(false);
        when(configService.get(URL_PADRAO_EVENTOS)).thenReturn(LINK_IMAGEM_PADRAO_EVENTOS);

        criarEventoRequestValidator.accept(request);

        verify(pegarLocalService).porId(request.getIdLocal());
        verify(eventoRepository).existsByTituloAndDataInicioAndDataFimAndLinkImagemAndCategoriaAndLocalEvento(
            request.getTitulo(),
            request.getDataInicio(),
            request.getDataFim(),
            LINK_IMAGEM_PADRAO_EVENTOS,
            request.getCategoria(),
            local);
        verify(configService).get(URL_PADRAO_EVENTOS);

        verifyZeroInteractions(messageService);
    }

}
