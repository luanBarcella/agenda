package br.com.agenda.mapper.local;

import static br.com.agenda.fixture.Fixture.make;
import static br.com.agenda.service.support.ConfigService.Key.URL_PADRAO_LOCAIS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.dto.request.local.CriarLocalRequest;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.service.support.ConfigService;

@RunWith(MockitoJUnitRunner.class)
public class CriarLocalRequestMapperTest {

    @InjectMocks
    private CriarLocalRequestMapper criarLocalRequestMapper;

    @Mock
    private ConfigService configService;

    @Test
    @DisplayName("Deve Transformar CriarLocalRequest em LocalEntity Corretamente")
    public void DeveTransformarCriarLocalRequestEmLocalEntityCorretamente(){
        final CriarLocalRequest request = make(CriarLocalRequest.builder()).build();
        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        final LocalEntity response = criarLocalRequestMapper.apply(request, cidade);

        verifyZeroInteractions(configService);

        assertNotNull(response);
        assertEquals(request.getNome(), response.getNome());
        assertEquals(request.getLinkImagem(), response.getLinkImagem());
        assertCidades(cidade, response.getCidade());

    }

    @Test
    @DisplayName("Deve Inserir Link de Imagem Padrão Quando o Mesmo Vier Nullo")
    public void DeveInserirLinkDeImagemPadraoQuandoOMesmoVierNullo(){
        final CriarLocalRequest request = make(CriarLocalRequest.builder())
            .linkImagem(null)
            .build();

        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        final LocalEntity response = criarLocalRequestMapper.apply(request, cidade);

        verify(configService).get(URL_PADRAO_LOCAIS);

        assertNotNull(response);
        assertEquals(request.getNome(), response.getNome());
        assertCidades(cidade, response.getCidade());
    }

    private void assertCidades(final CidadeEntity cidadeRequest, final CidadeEntity cidadeResponse){
        assertEquals(cidadeRequest.getId(), cidadeResponse.getId());
        assertEquals(cidadeRequest.getNome(), cidadeResponse.getNome());
        assertEquals(cidadeRequest.getUF(), cidadeResponse.getUF());
        assertEquals(cidadeRequest.getLinkImagem(), cidadeResponse.getLinkImagem());
        assertEquals(cidadeRequest.getLocais(), cidadeResponse.getLocais());
    }

}
