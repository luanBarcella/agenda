package br.com.agenda.service.pessoa;

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

import br.com.agenda.dto.request.pessoa.CriarPessoaRequest;
import br.com.agenda.dto.response.pessoa.CriarPessoaResponse;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.mapper.pessoa.CriarPessoaRequestMapper;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.PessoaEntity;
import br.com.agenda.repository.PessoaRepository;
import br.com.agenda.service.support.PegarCidadeService;
import br.com.agenda.validator.pessoa.CriarPessoaRequestValidator;

@RunWith(MockitoJUnitRunner.class)
public class CriarPessoaServiceTest {

    @InjectMocks
    private CriarPessoaService criarPessoaService;

    @Mock
    private PegarCidadeService pegarCidadeService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private CriarPessoaRequestValidator criarPessoaRequestValidator;

    @Mock
    private CriarPessoaRequestMapper criarPessoaRequestMapper;

    @Captor
    private ArgumentCaptor<PessoaEntity> pessoaEntityArgumentCaptor;

    @Test
    @DisplayName("Deve Criar Uma Pessoa Corretamente")
    public void DeveCriarUmaPessoaCorretamente(){
        final CriarPessoaRequest request = Fixture.make(CriarPessoaRequest.builder())
            .build();

        final CidadeEntity cidade = Fixture.make(CidadeEntity.builder())
            .build();

        final PessoaEntity pessoa = Fixture.make(PessoaEntity.builder())
                .build();

        when(pegarCidadeService.porId(request.getIdCidade())).thenReturn(cidade);
        when(criarPessoaRequestMapper.apply(request, cidade)).thenReturn(pessoa);

        final CriarPessoaResponse response = criarPessoaService.criar(request);

        verify(criarPessoaRequestValidator).accept(request);
        verify(pegarCidadeService).porId(request.getIdCidade());
        verify(criarPessoaRequestMapper).apply(request, cidade);
        verify(pessoaRepository).save(pessoaEntityArgumentCaptor.capture());

        assertNotNull(response);
        assertEquals(pessoaEntityArgumentCaptor.getValue().getId(), response.getIdPessoa());
    }

}
