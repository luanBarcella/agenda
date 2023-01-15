package br.com.agenda.service.support;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.CIDADE_ID_INVALIDO;
import static br.com.agenda.fixture.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.repository.CidadeRepository;

@RunWith(MockitoJUnitRunner.class)
public class PegarCidadeServiceTest {

    private static final String MENSAGEM_ID_CIDADE_INVALIDO = "Id de cidade inválido.";

    @InjectMocks
    private PegarCidadeService pegarCidadeService;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private MessageService messageService;

    @Test
    @DisplayName("Deve Retornar a Cidade do Id Solicitado Corretamente")
    public void DeveRetornarACidadeDoIdSolicitadoCorretamente(){
        final Long idCidade = 1L;

        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        when(cidadeRepository.findById(idCidade)).thenReturn(Optional.of(cidade));

        final CidadeEntity response = pegarCidadeService.porId(idCidade);

        verify(cidadeRepository).findById(idCidade);

        assertNotNull(response);
        assertEquals(cidade.getId(), response.getId());
        assertEquals(cidade.getNome(), response.getNome());
        assertEquals(cidade.getUF(), response.getUF());
        assertEquals(cidade.getLinkImagem(), response.getLinkImagem());
        assertNotNull(response.getLocais());
        assertEquals(cidade.getLocais(), response.getLocais());

    }

    @Test
    @DisplayName("Deve Retornar Erro Quando Id da Cidade For Inválido")
    public void DeveRetornarErroQuandoIdDaCidadeForInvalido(){
        final Long idInvalido = 50000L;

        when(cidadeRepository.findById(idInvalido)).thenReturn(Optional.empty());
        when(messageService.get(CIDADE_ID_INVALIDO)).thenReturn(MENSAGEM_ID_CIDADE_INVALIDO);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            pegarCidadeService.porId(idInvalido);
        });

        verify(cidadeRepository).findById(idInvalido);
        verify(messageService).get(CIDADE_ID_INVALIDO);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(MENSAGEM_ID_CIDADE_INVALIDO, exception.getMessage());
        assertTrue(exception.getDetails().isEmpty());

    }

}
