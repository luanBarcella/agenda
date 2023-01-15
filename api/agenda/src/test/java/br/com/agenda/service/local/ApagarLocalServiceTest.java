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

import br.com.agenda.domain.Message;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.repository.LocalRepository;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.service.support.PegarLocalService;
import br.com.agenda.dto.response.local.ApagarLocalResponse;
import br.com.agenda.fixture.Fixture;

@RunWith(MockitoJUnitRunner.class)
public class ApagarLocalServiceTest {

    private static final String LOCAL_APAGADO_COM_SUCESSO = "Local apagado com sucesso.";

    @InjectMocks
    private ApagarLocalService apagarLocalService;

    @Mock
    private PegarLocalService pegarLocalService;

    @Mock
    private LocalRepository localRepository;

    @Mock
    private MessageService messageService;

    @Test
    @DisplayName("Deve Apagar Um Local Corretamente")
    public void DeveApagarUmLocalCorretamente(){
        final Long localId = 1L;

        final LocalEntity local = Fixture.make(LocalEntity.builder())
            .id(localId)
            .build();

        when(pegarLocalService.porId(localId)).thenReturn(local);
        when(messageService.get(Message.LOCAL_APAGADO_SUCESSO)).thenReturn(LOCAL_APAGADO_COM_SUCESSO);

        final ApagarLocalResponse response = apagarLocalService.apagar(localId);

        verify(pegarLocalService).porId(localId);
        verify(localRepository).delete(local);
        verify(messageService).get(Message.LOCAL_APAGADO_SUCESSO);

        assertNotNull(response);
        assertEquals(LOCAL_APAGADO_COM_SUCESSO, response.getMensagem());
    }
}
