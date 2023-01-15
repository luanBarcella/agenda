package br.com.agenda.service.pessoa;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.agenda.dto.response.pessoa.ConsultarPessoaResponse;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.PessoaEntity;
import br.com.agenda.repository.PessoaRepository;

@RunWith(MockitoJUnitRunner.class)
public class ConsultarPessoaServiceTest {

    @InjectMocks
    private ConsultarPessoaService consultarPessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Test
    @DisplayName("Deve Consultar As Pessoas Corretamente")
    public void DeveConsultarAsPessoasCorretamente(){
        final Pageable pageable = PageRequest.of(0, 10);

        final PessoaEntity pessoa = Fixture.make(PessoaEntity.builder()).build();
        final List<PessoaEntity> pessoas = asList(pessoa, pessoa, pessoa);

        when(pessoaRepository.findAll(pageable)).thenReturn(new PageImpl<>(pessoas));

        final Page<ConsultarPessoaResponse> response = consultarPessoaService.consultar(pageable);

        verify(pessoaRepository).findAll(pageable);

        assertNotNull(response);
        assertEquals(pessoas.size(), response.getContent().size());
    }

}
