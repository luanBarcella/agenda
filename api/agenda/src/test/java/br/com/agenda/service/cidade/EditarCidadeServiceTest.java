package br.com.agenda.service.cidade;

import static br.com.agenda.domain.Message.CAMPOS_ALTERADOS_SUCESSO;
import static br.com.agenda.domain.Message.LINK_IMAGEM_ALTERADO_SUCESSO;
import static br.com.agenda.domain.Message.NENHUM_CAMPO_ALTERADO;
import static br.com.agenda.domain.Message.NOME_ALTERADO_SUCESSO;
import static br.com.agenda.domain.Message.UF_ALTERADO_SUCESSO;
import static br.com.agenda.fixture.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.validator.cidade.EditarCidadeRequestValidator;
import br.com.agenda.dto.request.cidade.EditarCidadeRequest;
import br.com.agenda.dto.response.cidade.EditarCidadeResponse;
import br.com.agenda.repository.CidadeRepository;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.service.support.PegarCidadeService;

@RunWith(MockitoJUnitRunner.class)
public class EditarCidadeServiceTest {

    private static final String MENSAGEM_NOME_ALTERADO_SUCESSO = "Nome alterado com sucesso.";
    private static final String MENSAGEM_UF_ALTERADO_SUCESSO = "UF alterado com sucesso.";
    private static final String MENSAGEM_LINK_IMAGEM_ALTERADO_SUCESSO = "Link de imagem alterado com sucesso.";
    private static final String MENSAGEM_CAMPOS_ALTERADOS_SUCESSO = "Campo(s) alterado(s) com sucesso: ";
    private static final String MENSAGEM_NENHUM_CAMPO_ALTERADO = "Nenhum campo alterado.";

    @InjectMocks
    private EditarCidadeService editarCidadeService;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private MessageService messageService;

    @Mock
    private PegarCidadeService pegarCidadeService;

    @Mock
    private EditarCidadeRequestValidator editarCidadeRequestValidator;

    @Captor
    private ArgumentCaptor<CidadeEntity> cidadeEntityArgumentCaptor;

    @Test
    @DisplayName("Deve Alterar Todos os Campos Corretamente")
    public void DeveAlterarTodosOsCamposCorretamente(){
        final Long idCidade = 1L;
        final EditarCidadeRequest request = make(EditarCidadeRequest.builder()).build();

        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        when(pegarCidadeService.porId(idCidade)).thenReturn(cidade);
        when(messageService.get(NOME_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_NOME_ALTERADO_SUCESSO);
        when(messageService.get(UF_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_UF_ALTERADO_SUCESSO);
        when(messageService.get(LINK_IMAGEM_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_LINK_IMAGEM_ALTERADO_SUCESSO);
        when(messageService.get(CAMPOS_ALTERADOS_SUCESSO)).thenReturn(MENSAGEM_CAMPOS_ALTERADOS_SUCESSO);

        final EditarCidadeResponse response = editarCidadeService.editar(idCidade, request);

        verify(pegarCidadeService).porId(idCidade);
        verify(editarCidadeRequestValidator).accept(request, cidade);
        verify(messageService).get(NOME_ALTERADO_SUCESSO);
        verify(messageService).get(UF_ALTERADO_SUCESSO);
        verify(messageService).get(LINK_IMAGEM_ALTERADO_SUCESSO);
        verify(cidadeRepository).save(cidadeEntityArgumentCaptor.capture());
        verify(messageService).get(CAMPOS_ALTERADOS_SUCESSO);

        final CidadeEntity cidadeCapturada = cidadeEntityArgumentCaptor.getValue();
        final String mensagemEsperadaCamposAlteradosComSucesso = MENSAGEM_CAMPOS_ALTERADOS_SUCESSO + response.getCamposAlterados().size();

        assertNotNull(response);
        assertNotNull(response.getCamposAlterados());
        assertEquals(request.getNome(), cidadeCapturada.getNome());
        assertEquals(request.getUF(), cidadeCapturada.getUF());
        assertEquals(request.getLinkImagem(), cidadeCapturada.getLinkImagem());
        assertEquals(mensagemEsperadaCamposAlteradosComSucesso, response.getMensagem());
        assertEquals(MENSAGEM_NOME_ALTERADO_SUCESSO, response.getCamposAlterados().get(0));
        assertEquals(MENSAGEM_UF_ALTERADO_SUCESSO, response.getCamposAlterados().get(1));
        assertEquals(MENSAGEM_LINK_IMAGEM_ALTERADO_SUCESSO, response.getCamposAlterados().get(2));

    }

    @Test
    @DisplayName("Não Deve Alterar Nenhum Campo Quando Todos os Atributos Forem Null")
    public void naoDeveAlterarNenhumCampoQuandoTodosOsAtributosForemNull(){
        final Long idCidade = 1L;
        final EditarCidadeRequest request = make(EditarCidadeRequest.builder())
            .nome(null)
            .UF(null)
            .linkImagem(null)
            .build();

        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        when(pegarCidadeService.porId(idCidade)).thenReturn(cidade);
        when(messageService.get(NENHUM_CAMPO_ALTERADO)).thenReturn(MENSAGEM_NENHUM_CAMPO_ALTERADO);

        final EditarCidadeResponse response = editarCidadeService.editar(idCidade, request);

        verify(pegarCidadeService).porId(idCidade);
        verify(editarCidadeRequestValidator).accept(request, cidade);
        verify(cidadeRepository).save(cidadeEntityArgumentCaptor.capture());
        verify(messageService).get(NENHUM_CAMPO_ALTERADO);

        final CidadeEntity cidadeCapturada = cidadeEntityArgumentCaptor.getValue();
        assertNotNull(response);
        assertNotNull(response.getCamposAlterados());
        assertEquals(cidade.getNome(), cidadeCapturada.getNome());
        assertEquals(cidade.getUF(), cidadeCapturada.getUF());
        assertEquals(cidade.getLinkImagem(), cidadeCapturada.getLinkImagem());
        assertEquals(MENSAGEM_NENHUM_CAMPO_ALTERADO, response.getMensagem());
        assertTrue(response.getCamposAlterados().isEmpty());
    }

    @Test
    @DisplayName("Não Deve Alterar o Nome Quando o Mesmo For Nullo")
    public void NaoDeveAlterarONomeQuandoOMesmoForNullo(){
        final Long idCidade = 1L;
        final EditarCidadeRequest request = make(EditarCidadeRequest.builder())
            .nome(null)
            .build();

        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        when(pegarCidadeService.porId(idCidade)).thenReturn(cidade);
        when(messageService.get(UF_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_UF_ALTERADO_SUCESSO);
        when(messageService.get(LINK_IMAGEM_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_LINK_IMAGEM_ALTERADO_SUCESSO);
        when(messageService.get(CAMPOS_ALTERADOS_SUCESSO)).thenReturn(MENSAGEM_CAMPOS_ALTERADOS_SUCESSO);

        final EditarCidadeResponse response = editarCidadeService.editar(idCidade, request);

        verify(pegarCidadeService).porId(idCidade);
        verify(editarCidadeRequestValidator).accept(request, cidade);
        verify(messageService).get(UF_ALTERADO_SUCESSO);
        verify(messageService).get(LINK_IMAGEM_ALTERADO_SUCESSO);
        verify(cidadeRepository).save(cidadeEntityArgumentCaptor.capture());
        verify(messageService).get(CAMPOS_ALTERADOS_SUCESSO);

        final CidadeEntity cidadeCapturada = cidadeEntityArgumentCaptor.getValue();
        final String mensagemEsperadaCamposAlteradosComSucesso = MENSAGEM_CAMPOS_ALTERADOS_SUCESSO + response.getCamposAlterados().size();

        assertNotNull(response);
        assertNotNull(response.getCamposAlterados());
        assertEquals(cidade.getNome(), cidadeCapturada.getNome());
        assertEquals(request.getUF(), cidadeCapturada.getUF());
        assertEquals(request.getLinkImagem(), cidadeCapturada.getLinkImagem());
        assertEquals(mensagemEsperadaCamposAlteradosComSucesso, response.getMensagem());
        assertEquals(MENSAGEM_UF_ALTERADO_SUCESSO, response.getCamposAlterados().get(0));
        assertEquals(MENSAGEM_LINK_IMAGEM_ALTERADO_SUCESSO, response.getCamposAlterados().get(1));

    }

    @Test
    @DisplayName("Não Deve Alterar o Nome Quando o Mesmo For Uma String Vazia ou em Branco")
    public void NaoDeveAlterarONomeQuandoOMesmoForUmaStringVaziaOuEmBranco(){
        final Long idCidade = 1L;
        final EditarCidadeRequest request = make(EditarCidadeRequest.builder())
            .nome("   ")
            .build();

        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        when(pegarCidadeService.porId(idCidade)).thenReturn(cidade);
        when(messageService.get(UF_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_UF_ALTERADO_SUCESSO);
        when(messageService.get(LINK_IMAGEM_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_LINK_IMAGEM_ALTERADO_SUCESSO);
        when(messageService.get(CAMPOS_ALTERADOS_SUCESSO)).thenReturn(MENSAGEM_CAMPOS_ALTERADOS_SUCESSO);

        final EditarCidadeResponse response = editarCidadeService.editar(idCidade, request);

        verify(pegarCidadeService).porId(idCidade);
        verify(editarCidadeRequestValidator).accept(request, cidade);
        verify(messageService).get(UF_ALTERADO_SUCESSO);
        verify(messageService).get(LINK_IMAGEM_ALTERADO_SUCESSO);
        verify(cidadeRepository).save(cidadeEntityArgumentCaptor.capture());
        verify(messageService).get(CAMPOS_ALTERADOS_SUCESSO);

        final CidadeEntity cidadeCapturada = cidadeEntityArgumentCaptor.getValue();
        final String mensagemEsperadaCamposAlteradosComSucesso = MENSAGEM_CAMPOS_ALTERADOS_SUCESSO + response.getCamposAlterados().size();

        assertNotNull(response);
        assertNotNull(response.getCamposAlterados());
        assertEquals(cidade.getNome(), cidadeCapturada.getNome());
        assertEquals(request.getUF(), cidadeCapturada.getUF());
        assertEquals(request.getLinkImagem(), cidadeCapturada.getLinkImagem());
        assertEquals(mensagemEsperadaCamposAlteradosComSucesso, response.getMensagem());
        assertEquals(MENSAGEM_UF_ALTERADO_SUCESSO, response.getCamposAlterados().get(0));
        assertEquals(MENSAGEM_LINK_IMAGEM_ALTERADO_SUCESSO, response.getCamposAlterados().get(1));

    }

    @Test
    @DisplayName("Nao Deve Alterar a UF Quando a Mesma For Nulla")
    public void NaoDeveAlterarAUFQuandoAMesmaForNulla(){
        final Long idCidade = 1L;
        final EditarCidadeRequest request = make(EditarCidadeRequest.builder())
            .UF(null)
            .build();

        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        when(pegarCidadeService.porId(idCidade)).thenReturn(cidade);
        when(messageService.get(NOME_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_NOME_ALTERADO_SUCESSO);
        when(messageService.get(LINK_IMAGEM_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_LINK_IMAGEM_ALTERADO_SUCESSO);
        when(messageService.get(CAMPOS_ALTERADOS_SUCESSO)).thenReturn(MENSAGEM_CAMPOS_ALTERADOS_SUCESSO);

        final EditarCidadeResponse response = editarCidadeService.editar(idCidade, request);

        verify(pegarCidadeService).porId(idCidade);
        verify(editarCidadeRequestValidator).accept(request, cidade);
        verify(messageService).get(NOME_ALTERADO_SUCESSO);
        verify(messageService).get(LINK_IMAGEM_ALTERADO_SUCESSO);
        verify(cidadeRepository).save(cidadeEntityArgumentCaptor.capture());
        verify(messageService).get(CAMPOS_ALTERADOS_SUCESSO);

        final CidadeEntity cidadeCapturada = cidadeEntityArgumentCaptor.getValue();
        final String mensagemEsperadaCamposAlteradosComSucesso = MENSAGEM_CAMPOS_ALTERADOS_SUCESSO + response.getCamposAlterados().size();

        assertNotNull(response);
        assertNotNull(response.getCamposAlterados());
        assertEquals(request.getNome(), cidadeCapturada.getNome());
        assertEquals(cidade.getUF(), cidadeCapturada.getUF());
        assertEquals(request.getLinkImagem(), cidadeCapturada.getLinkImagem());
        assertEquals(mensagemEsperadaCamposAlteradosComSucesso, response.getMensagem());
        assertEquals(MENSAGEM_NOME_ALTERADO_SUCESSO, response.getCamposAlterados().get(0));
        assertEquals(MENSAGEM_LINK_IMAGEM_ALTERADO_SUCESSO, response.getCamposAlterados().get(1));

    }

    @Test
    @DisplayName("Não Deve Alterar a UF Quando a Mesma For Uma String Vazia ou em Branco")
    public void naoDeveAlterarAUFQuandoAMesmaForUmaStringVaziaOuEmBranco(){
        final Long idCidade = 1L;
        final EditarCidadeRequest request = make(EditarCidadeRequest.builder())
            .UF("     ")
            .build();

        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        when(pegarCidadeService.porId(idCidade)).thenReturn(cidade);
        when(messageService.get(NOME_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_NOME_ALTERADO_SUCESSO);
        when(messageService.get(LINK_IMAGEM_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_LINK_IMAGEM_ALTERADO_SUCESSO);
        when(messageService.get(CAMPOS_ALTERADOS_SUCESSO)).thenReturn(MENSAGEM_CAMPOS_ALTERADOS_SUCESSO);

        final EditarCidadeResponse response = editarCidadeService.editar(idCidade, request);

        verify(pegarCidadeService).porId(idCidade);
        verify(editarCidadeRequestValidator).accept(request, cidade);
        verify(messageService).get(NOME_ALTERADO_SUCESSO);
        verify(messageService).get(LINK_IMAGEM_ALTERADO_SUCESSO);
        verify(cidadeRepository).save(cidadeEntityArgumentCaptor.capture());
        verify(messageService).get(CAMPOS_ALTERADOS_SUCESSO);

        final CidadeEntity cidadeCapturada = cidadeEntityArgumentCaptor.getValue();
        final String mensagemEsperadaCamposAlteradosComSucesso = MENSAGEM_CAMPOS_ALTERADOS_SUCESSO + response.getCamposAlterados().size();

        assertNotNull(response);
        assertNotNull(response.getCamposAlterados());
        assertEquals(request.getNome(), cidadeCapturada.getNome());
        assertEquals(cidade.getUF(), cidadeCapturada.getUF());
        assertEquals(request.getLinkImagem(), cidadeCapturada.getLinkImagem());
        assertEquals(mensagemEsperadaCamposAlteradosComSucesso, response.getMensagem());
        assertEquals(MENSAGEM_NOME_ALTERADO_SUCESSO, response.getCamposAlterados().get(0));
        assertEquals(MENSAGEM_LINK_IMAGEM_ALTERADO_SUCESSO, response.getCamposAlterados().get(1));

    }

    @Test
    @DisplayName("Não Deve Alterar o Link da Imagem Quando o Mesmo For Nullo")
    public void NaoDeveAlterarOLinkDaImagemQuandoOMesmoForNullo(){
        final Long idCidade = 1L;
        final EditarCidadeRequest request = make(EditarCidadeRequest.builder())
            .linkImagem(null)
            .build();

        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        when(pegarCidadeService.porId(idCidade)).thenReturn(cidade);
        when(messageService.get(NOME_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_NOME_ALTERADO_SUCESSO);
        when(messageService.get(UF_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_UF_ALTERADO_SUCESSO);
        when(messageService.get(CAMPOS_ALTERADOS_SUCESSO)).thenReturn(MENSAGEM_CAMPOS_ALTERADOS_SUCESSO);

        final EditarCidadeResponse response = editarCidadeService.editar(idCidade, request);

        verify(pegarCidadeService).porId(idCidade);
        verify(editarCidadeRequestValidator).accept(request, cidade);
        verify(messageService).get(NOME_ALTERADO_SUCESSO);
        verify(messageService).get(UF_ALTERADO_SUCESSO);
        verify(cidadeRepository).save(cidadeEntityArgumentCaptor.capture());
        verify(messageService).get(CAMPOS_ALTERADOS_SUCESSO);

        final CidadeEntity cidadeCapturada = cidadeEntityArgumentCaptor.getValue();
        final String mensagemEsperadaCamposAlteradosComSucesso = MENSAGEM_CAMPOS_ALTERADOS_SUCESSO + response.getCamposAlterados().size();

        assertNotNull(response);
        assertNotNull(response.getCamposAlterados());
        assertEquals(request.getNome(), cidadeCapturada.getNome());
        assertEquals(request.getUF(), cidadeCapturada.getUF());
        assertEquals(cidade.getLinkImagem(), cidadeCapturada.getLinkImagem());
        assertEquals(mensagemEsperadaCamposAlteradosComSucesso, response.getMensagem());
        assertEquals(MENSAGEM_NOME_ALTERADO_SUCESSO, response.getCamposAlterados().get(0));
        assertEquals(MENSAGEM_UF_ALTERADO_SUCESSO, response.getCamposAlterados().get(1));

    }

    @Test
    @DisplayName("Não Deve Alterar o Link da Imagem Quando o Mesmo For Uma String Vazia ou em Branco")
    public void NaoDeveAlterarOLinkDaImagemQuandoOMesmoForUmaStringVaziaOuEmBranco(){
        final Long idCidade = 1L;
        final EditarCidadeRequest request = make(EditarCidadeRequest.builder())
            .linkImagem("     ")
            .build();

        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        when(pegarCidadeService.porId(idCidade)).thenReturn(cidade);
        when(messageService.get(NOME_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_NOME_ALTERADO_SUCESSO);
        when(messageService.get(UF_ALTERADO_SUCESSO)).thenReturn(MENSAGEM_UF_ALTERADO_SUCESSO);
        when(messageService.get(CAMPOS_ALTERADOS_SUCESSO)).thenReturn(MENSAGEM_CAMPOS_ALTERADOS_SUCESSO);

        final EditarCidadeResponse response = editarCidadeService.editar(idCidade, request);

        verify(pegarCidadeService).porId(idCidade);
        verify(editarCidadeRequestValidator).accept(request, cidade);
        verify(messageService).get(NOME_ALTERADO_SUCESSO);
        verify(messageService).get(UF_ALTERADO_SUCESSO);
        verify(cidadeRepository).save(cidadeEntityArgumentCaptor.capture());
        verify(messageService).get(CAMPOS_ALTERADOS_SUCESSO);

        final CidadeEntity cidadeCapturada = cidadeEntityArgumentCaptor.getValue();
        final String mensagemEsperadaCamposAlteradosComSucesso = MENSAGEM_CAMPOS_ALTERADOS_SUCESSO + response.getCamposAlterados().size();

        assertNotNull(response);
        assertNotNull(response.getCamposAlterados());
        assertEquals(request.getNome(), cidadeCapturada.getNome());
        assertEquals(request.getUF(), cidadeCapturada.getUF());
        assertEquals(cidade.getLinkImagem(), cidadeCapturada.getLinkImagem());
        assertEquals(mensagemEsperadaCamposAlteradosComSucesso, response.getMensagem());
        assertEquals(MENSAGEM_NOME_ALTERADO_SUCESSO, response.getCamposAlterados().get(0));
        assertEquals(MENSAGEM_UF_ALTERADO_SUCESSO, response.getCamposAlterados().get(1));

    }

}
