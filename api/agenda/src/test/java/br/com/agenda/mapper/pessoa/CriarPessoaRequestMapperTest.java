package br.com.agenda.mapper.pessoa;

import static br.com.agenda.service.support.ConfigService.Key.URL_PADRAO_PESSOAS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.agenda.dto.request.pessoa.CriarPessoaRequest;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.model.PessoaEntity;
import br.com.agenda.service.support.ConfigService;

@RunWith(MockitoJUnitRunner.class)
public class CriarPessoaRequestMapperTest {

    private final static String LINK_PADRAO_PARA_PESSOAS_SEM_IMAGEM = "https://images.unsplash.com/photo-1510723185481-c39848b105c0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fGFub255bW91c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60";

    @InjectMocks
    private CriarPessoaRequestMapper criarPessoaRequestMapper;

    @Mock
    private ConfigService configService;

    @Test
    @DisplayName("Deve Transformar CriarPessoaRequest em PessoaEntity Corretamente")
    public void DeveTransformarCriarPessoaRequestEmPessoaEntityCorretamente(){
        final Long idCidade = 1L;
        final CriarPessoaRequest request = Fixture.make(CriarPessoaRequest.builder())
            .idCidade(idCidade)
            .build();

        final CidadeEntity cidade = Fixture.make(CidadeEntity.builder())
            .id(idCidade)
            .build();

        final PessoaEntity pessoaResponse = criarPessoaRequestMapper.apply(request, cidade);

        verifyZeroInteractions(configService);

        assertPessoaSemImagem(request, cidade, pessoaResponse);
        assertEquals(request.getLinkImagem(), pessoaResponse.getLinkImagem());
    }

    @Test
    @DisplayName("Deve Retornar Link Padrão de Imagem Quando o Campo Vier Com Valor Null")
    public void DeveRetornarLinkPadraoDeImagemQuandoCampoVierComValorNull(){
        final Long idCidade = 1L;
        final CriarPessoaRequest request = Fixture.make(CriarPessoaRequest.builder())
            .idCidade(idCidade)
            .linkImagem(null)
            .build();

        final CidadeEntity cidade = Fixture.make(CidadeEntity.builder())
            .id(idCidade)
            .build();

        when(configService.get(URL_PADRAO_PESSOAS)).thenReturn(LINK_PADRAO_PARA_PESSOAS_SEM_IMAGEM);

        final PessoaEntity pessoaResponse = criarPessoaRequestMapper.apply(request, cidade);

        verify(configService).get(URL_PADRAO_PESSOAS);

        assertPessoaSemImagem(request, cidade, pessoaResponse);
        assertEquals(LINK_PADRAO_PARA_PESSOAS_SEM_IMAGEM, pessoaResponse.getLinkImagem());
    }

    private void assertPessoaSemImagem(final CriarPessoaRequest request, final CidadeEntity cidade, final PessoaEntity pessoa){
        assertNotNull(pessoa);
        assertEquals(request.getNome(), pessoa.getNome());
        assertEquals(request.getIdade(), pessoa.getIdade());
        assertEquals(request.getEmail(), pessoa.getEmail());
        assertEquals(request.getIdCidade(), pessoa.getCidade().getId());
        assertCidade(cidade, pessoa.getCidade());
    }

    private void assertCidade(final CidadeEntity expected, final CidadeEntity response){
        assertNotNull(response);
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getUF(), response.getUF());
        assertEquals(expected.getLinkImagem(), response.getLinkImagem());
        assertEquals(expected.getNome(), response.getNome());
        assertLocais(expected.getLocais(), response.getLocais());
    }

    private void assertLocais(final List<LocalEntity> expected, final List<LocalEntity> response){
        assertNotNull(response);
        assertEquals(expected.size(), response.size());
        for(int i = 0; i < expected.size(); i++){
            assertEquals(expected.get(i).getId(), response.get(i).getId());
            assertEquals(expected.get(i).getNome(), response.get(i).getNome());
            assertEquals(expected.get(i).getLinkImagem(), response.get(i).getLinkImagem());
        }
    }
}
