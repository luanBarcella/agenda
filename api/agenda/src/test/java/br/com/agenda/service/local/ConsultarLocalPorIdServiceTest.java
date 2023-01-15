package br.com.agenda.service.local;

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

import br.com.agenda.dto.response.local.ConsultarLocalPorIdResponse;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.mapper.local.ConsultarLocalPorIdResponseMapper;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.service.support.PegarLocalService;

@RunWith(MockitoJUnitRunner.class)
public class ConsultarLocalPorIdServiceTest {

    private static final ConsultarLocalPorIdResponseMapper CONSULTAR_LOCAL_POR_ID_RESPONSE_MAPPER = new ConsultarLocalPorIdResponseMapper();

    @InjectMocks
    private ConsultarLocalPorIdService consultarLocalPorIdService;

    @Mock
    private PegarLocalService pegarLocalService;

    @Test
    @DisplayName("Deve Consultar o Local por Id Corretamente")
    public void DeveConsultarOLocalPorIdCorretamente(){
        final Long localId = 1L;

        final LocalEntity local = Fixture.make(LocalEntity.builder())
            .id(localId)
            .build();

        when(pegarLocalService.porId(localId)).thenReturn(local);

        final ConsultarLocalPorIdResponse response = consultarLocalPorIdService.consultar(localId);

        verify(pegarLocalService).porId(localId);

        assertNotNull(response);
        assertEquals(localId, response.getId());
    }

}
