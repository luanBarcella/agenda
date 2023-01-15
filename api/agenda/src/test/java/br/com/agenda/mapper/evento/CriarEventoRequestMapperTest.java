package br.com.agenda.mapper.evento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.dto.request.evento.CriarEventoRequest;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.EventoEntity;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.service.support.ConfigService;
import br.com.agenda.service.support.ConfigService.Key;

@RunWith(MockitoJUnitRunner.class)
public class CriarEventoRequestMapperTest {

    @InjectMocks
    private CriarEventoRequestMapper criarEventoRequestMapper;

    @Mock
    private ConfigService configService;

    @Test
    @DisplayName("Deve Converter CriarEventoRequest em EventoEntity Corretamente")
    public void DeveConverterCriarEventoRequestEmEventoEntityCorretamente(){
        final CriarEventoRequest request = Fixture.make(CriarEventoRequest.builder())
            .build();

        final LocalEntity local = Fixture.make(LocalEntity.builder())
            .build();

        final EventoEntity response = criarEventoRequestMapper.apply(request, local);

        verifyZeroInteractions(configService);

        assertNotNull(response);
        assertEquals(request.getTitulo(), response.getTitulo());
        assertEquals(request.getDataInicio(), response.getDataInicio());
        assertEquals(request.getDataFim(), response.getDataFim());
        Assertions.assertEquals(request.getCategoria(), response.getCategoria());
        assertEquals(request.getLinkImagem(), response.getLinkImagem());
        assertTrue(response.getPessoas().isEmpty());
        assertLocalEntity(local, response.getLocalEvento());

    }

    @Test
    @DisplayName("Deve Converter CriarEventoRequest em EventoEntity Corretamente Com LinkImagem Null")
    public void DeveConverterCriarEventoRequestEmEventoEntityCorretamenteComLinkImagemNull(){
        final CriarEventoRequest request = Fixture.make(CriarEventoRequest.builder())
            .linkImagem(null)
            .build();

        final LocalEntity local = Fixture.make(LocalEntity.builder())
            .build();

        final EventoEntity response = criarEventoRequestMapper.apply(request, local);

        verify(configService).get(Key.URL_PADRAO_EVENTOS);

        assertNotNull(response);
        assertEquals(request.getTitulo(), response.getTitulo());
        assertEquals(request.getDataInicio(), response.getDataInicio());
        assertEquals(request.getDataFim(), response.getDataFim());
        Assertions.assertEquals(request.getCategoria(), response.getCategoria());
        assertTrue(response.getPessoas().isEmpty());
        assertLocalEntity(local, response.getLocalEvento());

    }

    private void assertLocalEntity(final LocalEntity localEsperado, final LocalEntity localResponse){
        assertNotNull(localResponse);
        assertEquals(localEsperado.getId(), localResponse.getId());
        assertEquals(localEsperado.getLinkImagem(), localResponse.getLinkImagem());
        assertEquals(localEsperado.getNome(), localResponse.getNome());
        assertCidadeEntity(localEsperado.getCidade(), localResponse.getCidade());
    }

    private void assertCidadeEntity(final CidadeEntity cidadeEsperado, final CidadeEntity cidadeResponse){
        assertNotNull(cidadeResponse);
        assertEquals(cidadeEsperado.getUF(), cidadeResponse.getUF());
        assertEquals(cidadeEsperado.getId(), cidadeResponse.getId());
        assertEquals(cidadeEsperado.getLinkImagem(), cidadeResponse.getLinkImagem());
        assertEquals(cidadeEsperado.getNome(), cidadeResponse.getNome());
    }

}
