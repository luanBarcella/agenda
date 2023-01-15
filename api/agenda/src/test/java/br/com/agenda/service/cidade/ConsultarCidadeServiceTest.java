package br.com.agenda.service.cidade;

import static br.com.agenda.fixture.Fixture.make;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
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

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.dto.response.cidade.ConsultarCidadeResponse;
import br.com.agenda.repository.CidadeRepository;

@RunWith(MockitoJUnitRunner.class)
public class ConsultarCidadeServiceTest {

    @InjectMocks
    private ConsultarCidadeService consultarCidadeService;

    @Mock
    private CidadeRepository cidadeRepository;

    @Test
    @DisplayName("Deve Consultar as Cidades Corretamente")
    public void DeveConsultarAsCidadesCorretamente(){
        final Pageable pageable = PageRequest.of(0, 3);

        final Page<CidadeEntity> cidades = criarPageCidades();

        when(cidadeRepository.findAll(pageable)).thenReturn(cidades);

        final Page<ConsultarCidadeResponse> response = consultarCidadeService.consultar(pageable);

        verify(cidadeRepository).findAll(pageable);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(response.getSize(), cidades.getSize());

    }

    private Page<CidadeEntity> criarPageCidades(){
        final CidadeEntity cidade1 = make(CidadeEntity.builder()).build();
        final CidadeEntity cidade2 = make(CidadeEntity.builder()).build();
        final CidadeEntity cidade3 = make(CidadeEntity.builder()).build();

        final List<CidadeEntity> cidadesList = Arrays.asList(cidade1, cidade2, cidade3);

        return new PageImpl<>(cidadesList);
    }

}
