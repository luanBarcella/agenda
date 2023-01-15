package br.com.agenda.validator.pessoa;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.PESSOA_JA_REGISTRADA;
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

import br.com.agenda.dto.request.pessoa.CriarPessoaRequest;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.repository.PessoaRepository;
import br.com.agenda.service.support.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class CriarPessoaRequestValidatorTest {

    @InjectMocks
    private CriarPessoaRequestValidator criarPessoaRequestValidator;

    @Mock
    private MessageService messageService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Test
    @DisplayName("Deve Lançar ClientErrorException Quando Request For Null")
    public void deveLancarClientErrorExceptionQuandoRequestForNull(){
        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            criarPessoaRequestValidator.accept(null);
        });

        verify(messageService).get(REQUEST_NAO_INFORMADA);

        verifyZeroInteractions(pessoaRepository);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Deve Lançar CLientErrorException Quando Pessoa Já Existir")
    public void deveLancarClientErrorExceptionQuandoPessoaJaExistir(){
        final CriarPessoaRequest request = Fixture.make(CriarPessoaRequest.builder())
            .build();

        when(pessoaRepository.existsByEmail(request.getEmail())).thenReturn(true);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            criarPessoaRequestValidator.accept(request);
        });

        verify(pessoaRepository).existsByEmail(request.getEmail());
        verify(messageService).get(PESSOA_JA_REGISTRADA);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Sucesso")
    public void sucesso(){
        final CriarPessoaRequest request = Fixture.make(CriarPessoaRequest.builder())
            .build();

        when(pessoaRepository.existsByEmail(request.getEmail())).thenReturn(false);

        criarPessoaRequestValidator.accept(request);

        verify(pessoaRepository).existsByEmail(request.getEmail());

        verifyZeroInteractions(messageService);
    }

}
