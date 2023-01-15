package br.com.agenda.service.pessoa;

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

import br.com.agenda.service.support.PegarPessoaService;
import br.com.agenda.dto.response.pessoa.ConsultarPessoaPorIdResponse;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.PessoaEntity;

@RunWith(MockitoJUnitRunner.class)
public class ConsultarPessoaPorIdServiceTest {

    @InjectMocks
    private ConsultarPessoaPorIdService consultarPessoaPorIdService;

    @Mock
    private PegarPessoaService pegarPessoaService;

    @Test
    @DisplayName("Deve Consultar Pessoa Por Id Corretamente")
    public void DeveConsultarPessoaPorIdCorretamente(){
        final Long idPessoa = 1L;
        final PessoaEntity pessoa = Fixture.make(PessoaEntity.builder())
            .id(idPessoa)
            .build();

        when(pegarPessoaService.porId(idPessoa)).thenReturn(pessoa);

        final ConsultarPessoaPorIdResponse response = consultarPessoaPorIdService.consultar(idPessoa);

        verify(pegarPessoaService).porId(idPessoa);

        assertNotNull(response);
        assertEquals(idPessoa, response.getId());
    }

}
