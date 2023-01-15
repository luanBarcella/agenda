package br.com.agenda.service.cidade;

import static br.com.agenda.fixture.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.dto.response.cidade.ConsultarCidadePorIdResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.service.support.PegarCidadeService;

@RunWith(MockitoJUnitRunner.class)
public class ConsultarCidadePorIdServiceTest {

    @InjectMocks
    private ConsultarCidadePorIdService consultarCidadePorIdService;

    @Mock
    private PegarCidadeService pegarCidadeService;

    @Test
    @DisplayName("Deve Retornar A Cidade Por Id Corretamente")
    public void DeveRetornarACidadePorIdCorretamente(){
        final Long idCidade = 1L;

        final CidadeEntity cidade = make(CidadeEntity.builder())
            .id(idCidade)
            .build();

        when(pegarCidadeService.porId(idCidade)).thenReturn(cidade);

        final ConsultarCidadePorIdResponse response = consultarCidadePorIdService.consultar(idCidade);

        verify(pegarCidadeService).porId(idCidade);

        assertNotNull(response);
        assertEquals(cidade.getId(), response.getId());

    }

}
