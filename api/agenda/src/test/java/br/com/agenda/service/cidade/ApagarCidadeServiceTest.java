package br.com.agenda.service.cidade;

import static br.com.agenda.domain.Message.CIDADE_APAGADA_SUCESSO;
import static br.com.agenda.fixture.Fixture.make;
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

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.dto.response.cidade.ApagarCidadeResponse;
import br.com.agenda.repository.CidadeRepository;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.service.support.PegarCidadeService;

@RunWith(MockitoJUnitRunner.class)
public class ApagarCidadeServiceTest {

    private static final String MENSAGEM_CIDADE_APAGADA_SUCESSO = "Cidade apagada com sucesso.";

    @InjectMocks
    private ApagarCidadeService apagarCidadeService;

    @Mock
    private PegarCidadeService pegarCidadeService;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private MessageService messageService;

    @Test
    @DisplayName("Deve Apagar a Cidade do Id Solicitado Corretamente")
    public void DeveApagarACidadeDoIdSolicitadoCorretamente(){
        final Long idCidade = 1L;
        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        when(pegarCidadeService.porId(idCidade)).thenReturn(cidade);
        when(messageService.get(CIDADE_APAGADA_SUCESSO)).thenReturn(MENSAGEM_CIDADE_APAGADA_SUCESSO);

        final ApagarCidadeResponse response = apagarCidadeService.apagar(idCidade);

        verify(pegarCidadeService).porId(idCidade);
        verify(cidadeRepository).delete(cidade);
        verify(messageService).get(CIDADE_APAGADA_SUCESSO);

        assertNotNull(response);
        assertEquals(MENSAGEM_CIDADE_APAGADA_SUCESSO, response.getMensagem());
    }

}
