package br.com.agenda.mapper.cidade;

import static br.com.agenda.fixture.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.util.CollectionUtils.isEmpty;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.dto.request.cidade.CriarCidadeRequest;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.service.support.ConfigService;
import br.com.agenda.service.support.ConfigService.Key;

@RunWith(MockitoJUnitRunner.class)
public class CriarCidadeRequestMapperTest {

    @InjectMocks
    private CriarCidadeRequestMapper criarCidadeRequestMapper;

    @Mock
    private ConfigService configService;

    public static final String LINK_PADRAO_PARA_CIDADES_SEM_IMAGEM = "https://images.unsplash.com/photo-1523731407965-2430cd12f5e4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fGNpdHl8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60";

    @Test
    @DisplayName("Deve Retornar CidadeEntity Corretamente Com Link Imagem Dado")
    public void DeveRetornarCidadeEntityCorretamenteComLinkImagemDado(){
        final CriarCidadeRequest request = make(CriarCidadeRequest.builder())
            .build();

        final CidadeEntity response = criarCidadeRequestMapper.apply(request);

        verifyNoMoreInteractions(configService);

        assertEquals(request.getNome(), response.getNome());
        assertEquals(request.getUF(), response.getUF());
        assertEquals(request.getLinkImagem(), response.getLinkImagem());
        assertTrue(isEmpty(response.getLocais()));
        
    }

    @Test
    @DisplayName("Deve Retornar CidadeEntity Corretamente Com Link Imagem Padrão")
    public void DeveRetornarCidadeEntityCorretamenteComLinkImagemPadrao(){
        final CriarCidadeRequest request = make(CriarCidadeRequest.builder())
            .linkImagem(null)
            .build();

        when(configService.get(Key.URL_PADRAO_CIDADES)).thenReturn(LINK_PADRAO_PARA_CIDADES_SEM_IMAGEM);

        final CidadeEntity response = criarCidadeRequestMapper.apply(request);

        verify(configService).get(Key.URL_PADRAO_CIDADES);

        assertEquals(request.getNome(), response.getNome());
        assertEquals(request.getUF(), response.getUF());
        assertEquals(LINK_PADRAO_PARA_CIDADES_SEM_IMAGEM, response.getLinkImagem());
        assertTrue(isEmpty(response.getLocais()));
    }
}
