package br.com.agenda.validator.local;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.LOCAL_JA_REGISTRADO;
import static br.com.agenda.domain.Message.LOCAL_NAO_INFORMADO;
import static br.com.agenda.domain.Message.REQUEST_NAO_INFORMADA;
import static br.com.agenda.fixture.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.repository.LocalRepository;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.dto.request.local.EditarLocalRequest;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.model.LocalEntity;

@RunWith(MockitoJUnitRunner.class)
public class EditarLocalRequestValidatorTest {

    @InjectMocks
    private EditarLocalRequestValidator editarLocalRequestValidator;

    @Mock
    private MessageService messageService;

    @Mock
    private LocalRepository localRepository;

    @Test
    @DisplayName("Deve Lançar ClientErrorException Quando Request For Null")
    public void deveLancarClientErrorExceptionQuandoRequestForNull(){
        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
           editarLocalRequestValidator.accept(null, null);
        });

        verify(messageService).get(REQUEST_NAO_INFORMADA);
        verifyZeroInteractions(localRepository);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Deve Lançar ClientErrorException Quando Local For Null")
    public void deveLancarClientErrorExceptionQuandoLocalForNull(){
        final EditarLocalRequest request = make(EditarLocalRequest.builder())
            .build();

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            editarLocalRequestValidator.accept(request, null);
        });

        verify(messageService).get(LOCAL_NAO_INFORMADO);
        verifyZeroInteractions(localRepository);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Deve Lançar ClientErrorException Quando Local Já Existir")
    public void deveLancarClientErrorExceptionQuandoLocalJaExistir(){
        final EditarLocalRequest request = make(EditarLocalRequest.builder())
            .build();

        final LocalEntity local = make(LocalEntity.builder())
            .build();

        when(localRepository.existsByNomeAndLinkImagem(
            request.getNome(),
            request.getLinkImagem()
        )).thenReturn(true);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            editarLocalRequestValidator.accept(request, local);
        });

        verify(localRepository).existsByNomeAndLinkImagem(
            request.getNome(),
            request.getLinkImagem());
        verify(messageService).get(LOCAL_JA_REGISTRADO);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Sucesso")
    public void sucesso(){
        final EditarLocalRequest request = make(EditarLocalRequest.builder())
            .nome(null)
            .linkImagem(null)
            .build();

        final LocalEntity local = make(LocalEntity.builder())
            .build();

        when(localRepository.existsByNomeAndLinkImagem(
            local.getNome(),
            local.getLinkImagem()
        )).thenReturn(false);

        editarLocalRequestValidator.accept(request, local);

        verify(localRepository).existsByNomeAndLinkImagem(
            local.getNome(),
            local.getLinkImagem());

        verifyZeroInteractions(messageService);
    }

}
