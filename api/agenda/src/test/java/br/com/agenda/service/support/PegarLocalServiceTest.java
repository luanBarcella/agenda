package br.com.agenda.service.support;

import static br.com.agenda.domain.Message.LOCAL_ID_INVALIDO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.repository.LocalRepository;
import br.com.agenda.domain.ErrorType;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.LocalEntity;

@RunWith(MockitoJUnitRunner.class)
public class PegarLocalServiceTest {

    private static final String MANSAGEM_PADRAO_ID_LOCAL_INVALIDO = "Id de local inválido.";

    @InjectMocks
    private PegarLocalService pegarLocalService;

    @Mock
    private LocalRepository localRepository;

    @Mock
    private MessageService messageService;

    @Test
    @DisplayName("Deve Retornar Local Por Id Corretamente")
    public void DeveRetornarLocalPorIdCorretamente(){
        final Long localId = 1L;

        final LocalEntity local = Fixture.make(LocalEntity.builder())
            .id(localId)
            .build();

        when(localRepository.findById(localId)).thenReturn(Optional.of(local));

        final LocalEntity localResponse = pegarLocalService.porId(localId);

        verify(localRepository).findById(localId);
        verifyZeroInteractions(messageService);

        assertNotNull(localResponse);
        assertEquals(local.getId(), localResponse.getId());
    }

    @Test
    @DisplayName("Deve Retornar Erro Quando Optional Voltar Vazio")
    public void DeveRetornarErroQuandoOptionalVoltarVazio(){
        final Long localId = 1L;

        when(localRepository.findById(localId)).thenReturn(Optional.empty());
        when(messageService.get(LOCAL_ID_INVALIDO)).thenReturn(MANSAGEM_PADRAO_ID_LOCAL_INVALIDO);

        final ClientErrorException exception = assertThrows(ClientErrorException.class, () -> {
          pegarLocalService.porId(localId);
        });

        verify(localRepository).findById(localId);
        verify(messageService).get(LOCAL_ID_INVALIDO);

        assertNotNull(exception);
        assertEquals(ErrorType.VALIDATION, exception.getErrorType());
        assertEquals(MANSAGEM_PADRAO_ID_LOCAL_INVALIDO, exception.getMessage());
    }

}
