package br.com.agenda.validator.local;

import static br.com.agenda.domain.ErrorType.VALIDATION;
import static br.com.agenda.domain.Message.LOCAL_JA_REGISTRADO;
import static br.com.agenda.domain.Message.REQUEST_NAO_INFORMADA;
import static br.com.agenda.service.support.ConfigService.Key.URL_PADRAO_LOCAIS;
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

import br.com.agenda.dto.request.local.CriarLocalRequest;
import br.com.agenda.exception.ClientErrorException;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.repository.LocalRepository;
import br.com.agenda.service.support.ConfigService;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.service.support.PegarCidadeService;

@RunWith(MockitoJUnitRunner.class)
public class CriarLocalRequestValidatorTest {

    private static final String LINK_PADRAO_LOCAIS_SEM_IMAGEM = "https://images.unsplash.com/photo-1544997872-62aabbe63823?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fGxvY2FsfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60";

    @InjectMocks
    private CriarLocalRequestValidator criarLocalRequestValidator;

    @Mock
    private LocalRepository localRepository;

    @Mock
    private PegarCidadeService pegarCidadeService;

    @Mock
    private ConfigService configService;

    @Mock
    private MessageService messageService;

    @Test
    @DisplayName("Deve Lancar ClientErrorException Quando Request For Null")
    public void DeveLancarClientErrorExceptionQuandoRequestForNull(){
        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            criarLocalRequestValidator.accept(null);
        });

        verify(messageService).get(REQUEST_NAO_INFORMADA);

        verifyZeroInteractions(configService);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Deve Lancar ClientErrorException Quando Local Já Existir")
    public void DeveLancarClientErrorExceptionQuandoLocalJaExistir(){
        final CriarLocalRequest request = Fixture.make(CriarLocalRequest.builder())
            .build();

        final CidadeEntity cidade = Fixture.make(CidadeEntity.builder())
                .build();

        when(pegarCidadeService.porId(request.getIdCidade())).thenReturn(cidade);
        when(localRepository.existsByNomeAndLinkImagemAndCidade(
            request.getNome(),
            request.getLinkImagem(),
            cidade
        )).thenReturn(true);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            criarLocalRequestValidator.accept(request);
        });

        verify(pegarCidadeService).porId(request.getIdCidade());
        verify(localRepository).existsByNomeAndLinkImagemAndCidade(
            request.getNome(),
            request.getLinkImagem(),
            cidade);
        verify(messageService).get(LOCAL_JA_REGISTRADO);

        verifyZeroInteractions(configService);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
    }

    @Test
    @DisplayName("Sucesso")
    public void sucesso(){
        final CriarLocalRequest request = Fixture.make(CriarLocalRequest.builder())
            .linkImagem(null)
            .build();

        final CidadeEntity cidade = Fixture.make(CidadeEntity.builder())
            .build();

        when(pegarCidadeService.porId(request.getIdCidade())).thenReturn(cidade);
        when(configService.get(URL_PADRAO_LOCAIS)).thenReturn(LINK_PADRAO_LOCAIS_SEM_IMAGEM);
        when(localRepository.existsByNomeAndLinkImagemAndCidade(
            request.getNome(),
            LINK_PADRAO_LOCAIS_SEM_IMAGEM,
            cidade
        )).thenReturn(false);

        criarLocalRequestValidator.accept(request);

        verify(pegarCidadeService).porId(request.getIdCidade());
        verify(configService).get(URL_PADRAO_LOCAIS);
        verify(localRepository).existsByNomeAndLinkImagemAndCidade(
            request.getNome(),
            LINK_PADRAO_LOCAIS_SEM_IMAGEM,
            cidade);

        verifyZeroInteractions(messageService);

    }
}
