package br.com.agenda.service.local;

import static br.com.agenda.fixture.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import br.com.agenda.domain.Message;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.repository.LocalRepository;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.service.support.PegarLocalService;
import br.com.agenda.validator.local.EditarLocalRequestValidator;
import br.com.agenda.dto.request.local.EditarLocalRequest;
import br.com.agenda.dto.response.local.EditarLocalResponse;

@RunWith(MockitoJUnitRunner.class)
public class EditarLocalServiceTest {

    private static final String NOME_ALTERADO_COM_SUCESSO = "Nome alterado com sucesso.";
    private static final String LINK_IMAGEM_ALTERADO_COM_SUCESSO = "Link de imagem alterado com sucesso.";
    private static final String CAMPOS_ALTERADOS_COM_SUCESSO = "Campo(s) alterado(s) com sucesso: ";
    private static final String NENHUM_CAMPO_ALTERADO = "Nenhum campo alterado.";

    @InjectMocks
    private EditarLocalService editarLocalService;

    @Mock
    private PegarLocalService pegarLocalService;

    @Mock
    private MessageService messageService;

    @Mock
    private LocalRepository localRepository;

    @Mock
    private EditarLocalRequestValidator editarLocalRequestValidator;

    @Captor
    private ArgumentCaptor<LocalEntity> localEntityArgumentCaptor;

    @Test
    @DisplayName("Deve Editar Todos Os Campos Corretamente")
    public void DeveEditarTodosOsCamposCorretamente(){
        final Long localId = 1L;
        final EditarLocalRequest request = make(EditarLocalRequest.builder()).build();

        final LocalEntity local = make(LocalEntity.builder())
            .id(localId)
            .build();

        final int tamanhoEsperadoArrayCamposAlterados = 2;

        when(pegarLocalService.porId(localId)).thenReturn(local);
        when(messageService.get(Message.NOME_ALTERADO_SUCESSO)).thenReturn(NOME_ALTERADO_COM_SUCESSO);
        when(messageService.get(Message.LINK_IMAGEM_ALTERADO_SUCESSO)).thenReturn(LINK_IMAGEM_ALTERADO_COM_SUCESSO);
        when(messageService.get(Message.CAMPOS_ALTERADOS_SUCESSO)).thenReturn(CAMPOS_ALTERADOS_COM_SUCESSO);

        final EditarLocalResponse response = editarLocalService.editar(request, localId);

        verify(pegarLocalService).porId(localId);
        verify(editarLocalRequestValidator).accept(request, local);
        verify(messageService).get(Message.NOME_ALTERADO_SUCESSO);
        verify(messageService).get(Message.LINK_IMAGEM_ALTERADO_SUCESSO);
        verify(localRepository).save(local);
        verify(messageService).get(Message.CAMPOS_ALTERADOS_SUCESSO);

        assertNotNull(response);
        assertEquals(local.getNome(), request.getNome());
        assertEquals(local.getLinkImagem(), request.getLinkImagem());
        assertEquals(CAMPOS_ALTERADOS_COM_SUCESSO + tamanhoEsperadoArrayCamposAlterados, response.getMensagem());
        assertEquals(NOME_ALTERADO_COM_SUCESSO, response.getCamposAlterados().get(0));
        assertEquals(LINK_IMAGEM_ALTERADO_COM_SUCESSO, response.getCamposAlterados().get(1));

    }

    @Test
    @DisplayName("Não Deve Editar Nenhum Campo Quando Todos Os Atributos Forem Null")
    public void NaoDeveEditarNenhumCampoQuandoARequestForNulla(){
       final Long localId = 1L;
       final EditarLocalRequest request = make(EditarLocalRequest.builder())
           .nome(null)
           .linkImagem(null)
           .build();

       final LocalEntity local = make(LocalEntity.builder())
           .build();

       final int camposAlteradosExpected = 0;

       when(pegarLocalService.porId(localId)).thenReturn(local);
       when(messageService.get(Message.NENHUM_CAMPO_ALTERADO)).thenReturn(NENHUM_CAMPO_ALTERADO);

       final EditarLocalResponse response = editarLocalService.editar(request, localId);

       verify(pegarLocalService).porId(localId);
       verify(editarLocalRequestValidator).accept(request, local);
       verify(localRepository).save(local);
       verify(messageService).get(Message.NENHUM_CAMPO_ALTERADO);

       assertNotNull(response);
       assertEquals(NENHUM_CAMPO_ALTERADO, response.getMensagem());
       assertEquals(camposAlteradosExpected, response.getCamposAlterados().size());
    }

    @Test
    @DisplayName("Não Deve Editar Nome Quando o Mesmo For Nullo")
    public void NaoDeveEditarNomeQuandoOMesmoForNullo(){
        final Long localId = 1L;
        final EditarLocalRequest request = make(EditarLocalRequest.builder())
            .nome(null)
            .build();

        final LocalEntity local = make(LocalEntity.builder())
            .id(localId)
            .build();

        final int tamanhoEsperadoArrayCamposAlterados = 1;

        when(pegarLocalService.porId(localId)).thenReturn(local);
        when(messageService.get(Message.LINK_IMAGEM_ALTERADO_SUCESSO)).thenReturn(LINK_IMAGEM_ALTERADO_COM_SUCESSO);
        when(messageService.get(Message.CAMPOS_ALTERADOS_SUCESSO)).thenReturn(CAMPOS_ALTERADOS_COM_SUCESSO);

        final EditarLocalResponse response = editarLocalService.editar(request, localId);

        verify(pegarLocalService).porId(localId);
        verify(editarLocalRequestValidator).accept(request, local);
        verify(messageService).get(Message.LINK_IMAGEM_ALTERADO_SUCESSO);
        verify(localRepository).save(localEntityArgumentCaptor.capture());
        verify(messageService).get(Message.CAMPOS_ALTERADOS_SUCESSO);

        final LocalEntity localEditado = localEntityArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(local.getNome(), localEditado.getNome());
        assertEquals(local.getLinkImagem(), request.getLinkImagem());
        assertEquals(CAMPOS_ALTERADOS_COM_SUCESSO + tamanhoEsperadoArrayCamposAlterados, response.getMensagem());
        assertEquals(LINK_IMAGEM_ALTERADO_COM_SUCESSO, response.getCamposAlterados().get(0));

    }

    @Test
    @DisplayName("Não Deve Editar Nome Quando o Mesmo For Uma String Vazia")
    public void NaoDeveEditarNomeQuandoOMesmoForUmaStringVazia(){
        final Long localId = 1L;
        final EditarLocalRequest request = make(EditarLocalRequest.builder())
            .nome("    ")
            .build();

        final LocalEntity local = make(LocalEntity.builder())
            .id(localId)
            .build();

        final int tamanhoEsperadoArrayCamposAlterados = 1;

        when(pegarLocalService.porId(localId)).thenReturn(local);
        when(messageService.get(Message.LINK_IMAGEM_ALTERADO_SUCESSO)).thenReturn(LINK_IMAGEM_ALTERADO_COM_SUCESSO);
        when(messageService.get(Message.CAMPOS_ALTERADOS_SUCESSO)).thenReturn(CAMPOS_ALTERADOS_COM_SUCESSO);

        final EditarLocalResponse response = editarLocalService.editar(request, localId);

        verify(pegarLocalService).porId(localId);
        verify(editarLocalRequestValidator).accept(request, local);
        verify(messageService).get(Message.LINK_IMAGEM_ALTERADO_SUCESSO);
        verify(localRepository).save(localEntityArgumentCaptor.capture());
        verify(messageService).get(Message.CAMPOS_ALTERADOS_SUCESSO);

        final LocalEntity localEditado = localEntityArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(local.getNome(), localEditado.getNome());
        assertEquals(local.getLinkImagem(), request.getLinkImagem());
        assertEquals(CAMPOS_ALTERADOS_COM_SUCESSO + tamanhoEsperadoArrayCamposAlterados, response.getMensagem());
        assertEquals(LINK_IMAGEM_ALTERADO_COM_SUCESSO, response.getCamposAlterados().get(0));

    }

    @Test
    @DisplayName("Não Deve Editar Link de Imagem Quando o Mesmo For Nullo")
    public void NaoDeveEditarLinkDeImagemQuandoOMesmoForNullo(){
        final Long localId = 1L;
        final EditarLocalRequest request = make(EditarLocalRequest.builder())
            .linkImagem(null)
            .build();

        final LocalEntity local = make(LocalEntity.builder())
            .id(localId)
            .build();

        final int tamanhoEsperadoArrayCamposAlterados = 1;

        when(pegarLocalService.porId(localId)).thenReturn(local);
        when(messageService.get(Message.NOME_ALTERADO_SUCESSO)).thenReturn(NOME_ALTERADO_COM_SUCESSO);
        when(messageService.get(Message.CAMPOS_ALTERADOS_SUCESSO)).thenReturn(CAMPOS_ALTERADOS_COM_SUCESSO);

        final EditarLocalResponse response = editarLocalService.editar(request, localId);

        verify(pegarLocalService).porId(localId);
        verify(editarLocalRequestValidator).accept(request, local);
        verify(messageService).get(Message.NOME_ALTERADO_SUCESSO);
        verify(localRepository).save(localEntityArgumentCaptor.capture());
        verify(messageService).get(Message.CAMPOS_ALTERADOS_SUCESSO);

        final LocalEntity localEditado = localEntityArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(local.getNome(), request.getNome());
        assertEquals(local.getLinkImagem(), localEditado.getLinkImagem());
        assertEquals(CAMPOS_ALTERADOS_COM_SUCESSO + tamanhoEsperadoArrayCamposAlterados, response.getMensagem());
        assertEquals(NOME_ALTERADO_COM_SUCESSO, response.getCamposAlterados().get(0));

    }

    @Test
    @DisplayName("Não Deve Editar Link de Imagem Quando o Mesmo For Uma String Vazia")
    public void NaoDeveEditarLinkDeImagemQuandoOMesmoForUmaStringVazia(){
        final Long localId = 1L;
        final EditarLocalRequest request = make(EditarLocalRequest.builder())
            .linkImagem("    ")
            .build();

        final LocalEntity local = make(LocalEntity.builder())
            .id(localId)
            .build();

        final int tamanhoEsperadoArrayCamposAlterados = 1;

        when(pegarLocalService.porId(localId)).thenReturn(local);
        when(messageService.get(Message.NOME_ALTERADO_SUCESSO)).thenReturn(NOME_ALTERADO_COM_SUCESSO);
        when(messageService.get(Message.CAMPOS_ALTERADOS_SUCESSO)).thenReturn(CAMPOS_ALTERADOS_COM_SUCESSO);

        final EditarLocalResponse response = editarLocalService.editar(request, localId);

        verify(pegarLocalService).porId(localId);
        verify(editarLocalRequestValidator).accept(request, local);
        verify(messageService).get(Message.NOME_ALTERADO_SUCESSO);
        verify(localRepository).save(localEntityArgumentCaptor.capture());
        verify(messageService).get(Message.CAMPOS_ALTERADOS_SUCESSO);

        final LocalEntity localEditado = localEntityArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(local.getNome(), request.getNome());
        assertEquals(local.getLinkImagem(), localEditado.getLinkImagem());
        assertEquals(CAMPOS_ALTERADOS_COM_SUCESSO + tamanhoEsperadoArrayCamposAlterados, response.getMensagem());
        assertEquals(NOME_ALTERADO_COM_SUCESSO, response.getCamposAlterados().get(0));

    }

}
