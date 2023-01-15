package br.com.agenda.validator.cidade;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.CIDADE_JA_REGISTRADA;
import static br.com.agenda.domain.Message.CIDADE_NAO_INFORMADA;
import static br.com.agenda.domain.Message.REQUEST_NAO_INFORMADA;
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

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.dto.request.cidade.EditarCidadeRequest;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.repository.CidadeRepository;
import br.com.agenda.service.support.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class EditarCidadeRequestValidatorTest {

    @InjectMocks
    private EditarCidadeRequestValidator editarCidadeRequestValidator;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private MessageService messageService;

    @Test
    @DisplayName("Deve Retornar ClientErrorException Quando Request For Null")
    public void deveRetornarClientErrorExceptionQuandoRequestForNull(){
        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
           editarCidadeRequestValidator.accept(null, null);
        });

        verify(messageService).get(REQUEST_NAO_INFORMADA);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Deve Retornar ClientErrorException Quando Cidade For Null")
    public void deveRetornarClientErrorExceptionQuandoCidadeForNull(){
        final EditarCidadeRequest request = Fixture.make(EditarCidadeRequest.builder())
            .build();

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            editarCidadeRequestValidator.accept(request, null);
        });

        verify(messageService).get(CIDADE_NAO_INFORMADA);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Deve Retornar ClientErrorException Quando O Resultado Final Já Estiver Registrado")
    public void deveRetornarClientErrorExceptionQuandoResultadoFinalJaEstiverRegistrado(){
        final EditarCidadeRequest request = Fixture.make(EditarCidadeRequest.builder())
            .build();

        final CidadeEntity cidade = Fixture.make(CidadeEntity.builder())
            .build();

        when(cidadeRepository.existsByNomeAndUFAndLinkImagem(
            request.getNome(),
            request.getUF(),
            request.getLinkImagem()
        )).thenReturn(true);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            editarCidadeRequestValidator.accept(request, cidade);
        });

        verify(cidadeRepository).existsByNomeAndUFAndLinkImagem(
            request.getNome(),
            request.getUF(),
            request.getLinkImagem()
        );
        verify(messageService).get(CIDADE_JA_REGISTRADA);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Sucesso")
    public void sucesso(){
        final EditarCidadeRequest request = Fixture.make(EditarCidadeRequest.builder())
            .linkImagem(null)
            .UF(null)
            .nome(null)
            .build();

        final CidadeEntity cidade = Fixture.make(CidadeEntity.builder())
            .build();

        when(cidadeRepository.existsByNomeAndUFAndLinkImagem(
            cidade.getNome(),
            cidade.getUF(),
            cidade.getLinkImagem()
        )).thenReturn(false);

        editarCidadeRequestValidator.accept(request, cidade);


        verify(cidadeRepository).existsByNomeAndUFAndLinkImagem(
            cidade.getNome(),
            cidade.getUF(),
            cidade.getLinkImagem()
        );

        verifyZeroInteractions(messageService);
    }
}
