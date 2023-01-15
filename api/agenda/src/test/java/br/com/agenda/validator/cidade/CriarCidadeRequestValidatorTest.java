package br.com.agenda.validator.cidade;

import static br.com.agenda.service.support.ConfigService.Key.URL_PADRAO_CIDADES;
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

import br.com.agenda.dto.request.cidade.CriarCidadeRequest;
import br.com.agenda.domain.ErrorType;
import br.com.agenda.domain.Message;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.repository.CidadeRepository;
import br.com.agenda.service.support.ConfigService;
import br.com.agenda.service.support.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class CriarCidadeRequestValidatorTest {

    private static final String URL_PADRAO_IMAGEM_CIDADES = "https://images.unsplash.com/photo-1523731407965-2430cd12f5e4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fGNpdHl8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60";

    @InjectMocks
    private CriarCidadeRequestValidator criarCidadeRequestValidator;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private MessageService messageService;

    @Mock
    private ConfigService configService;

    @Test
    @DisplayName("Deve Lançar ClientErrorException Quando Request For Null")
    public void DeveLancarClientErrorExceptionQuandoRequestForNull(){
        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
           criarCidadeRequestValidator.accept(null);
        });

        verify(messageService).get(Message.REQUEST_NAO_INFORMADA);

        verifyZeroInteractions(configService);

        assertNotNull(exception);
        assertEquals(ErrorType.VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Deve Lançar ClientErrorException Quando Já Existir Cidade Com os Mesmos Dados")
    public void DeveLancarClientErrorExceptionQuandoJaExistirCidadeComOsMesmosDados(){
        final CriarCidadeRequest request = Fixture.make(CriarCidadeRequest.builder())
            .build();

        when(cidadeRepository.existsByNomeAndUFAndLinkImagem(
            request.getNome(),
            request.getUF(),
            request.getLinkImagem()
        )).thenReturn(true);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            criarCidadeRequestValidator.accept(request);
        });

        verify(cidadeRepository).existsByNomeAndUFAndLinkImagem(
            request.getNome(),
            request.getUF(),
            request.getLinkImagem());
        verify(messageService).get(Message.CIDADE_JA_REGISTRADA);

        verifyZeroInteractions(configService);

        assertNotNull(exception);
        assertEquals(ErrorType.VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Deve Usar Link Padrao Quando Link Imagem Vier Null")
    public void DeveUsarLinkPadraoQuandoLinkImagemVierNull(){
        final CriarCidadeRequest request = Fixture.make(CriarCidadeRequest.builder())
            .linkImagem(null)
            .build();

        when(configService.get(URL_PADRAO_CIDADES)).thenReturn(URL_PADRAO_IMAGEM_CIDADES);
        when(cidadeRepository.existsByNomeAndUFAndLinkImagem(
            request.getNome(),
            request.getUF(),
            URL_PADRAO_IMAGEM_CIDADES
        )).thenReturn(false);

        criarCidadeRequestValidator.accept(request);

        verify(configService).get(URL_PADRAO_CIDADES);
        verify(cidadeRepository).existsByNomeAndUFAndLinkImagem(
            request.getNome(),
            request.getUF(),
            URL_PADRAO_IMAGEM_CIDADES);

        verifyZeroInteractions(messageService);
    }

    @Test
    @DisplayName("Sucesso")
    public void sucesso(){
        final CriarCidadeRequest request = Fixture.make(CriarCidadeRequest.builder())
            .build();

        when(cidadeRepository.existsByNomeAndUFAndLinkImagem(
            request.getNome(),
            request.getUF(),
            request.getLinkImagem()
        )).thenReturn(false);

        criarCidadeRequestValidator.accept(request);

        verify(cidadeRepository).existsByNomeAndUFAndLinkImagem(
            request.getNome(),
            request.getUF(),
            request.getLinkImagem());

        verifyZeroInteractions(messageService);
    }

}
