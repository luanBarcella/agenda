package br.com.agenda.service.support;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.PESSOA_ID_INVALIDO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.PessoaEntity;
import br.com.agenda.repository.PessoaRepository;

@RunWith(MockitoJUnitRunner.class)
public class PegarPessoaServiceTest {

    private static final String MENSAGEM_ID_PESSOA_INVALIDO = "Id de pessoa inválido.";

    @InjectMocks
    private PegarPessoaService pegarPessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private MessageService messageService;

    @Test
    @DisplayName("Deve Retornar Cidade Por Id Corretamente")
    public void DeveRetornarCidadePorIdCorretamente(){
        final Long idPessoa = 1L;

        final PessoaEntity pessoa = Fixture.make(PessoaEntity.builder())
            .id(idPessoa)
            .build();

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoa));

        final PessoaEntity pessoaResponse = pegarPessoaService.porId(idPessoa);

        verify(pessoaRepository).findById(idPessoa);

        assertNotNull(pessoa);
        assertEquals(pessoa.getId(), pessoaResponse.getId());
    }

    @Test
    @DisplayName("Deve Retornar Quando Optional Voltar Vazio")
    public void DeveRetornarQuandoOptionalVoltarVazio(){
        final Long idPessoa = 1L;

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.empty());
        when(messageService.get(PESSOA_ID_INVALIDO)).thenReturn(MENSAGEM_ID_PESSOA_INVALIDO);

        final ClientErrorException exception = assertThrows(ClientErrorException.class, () -> {
            pegarPessoaService.porId(idPessoa);
        });

        verify(pessoaRepository).findById(idPessoa);
        verify(messageService).get(PESSOA_ID_INVALIDO);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(MENSAGEM_ID_PESSOA_INVALIDO, exception.getMessage());
    }

}
