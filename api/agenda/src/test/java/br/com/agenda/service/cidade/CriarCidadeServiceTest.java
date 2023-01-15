package br.com.agenda.service.cidade;

import static br.com.agenda.fixture.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import br.com.agenda.dto.request.cidade.CriarCidadeRequest;
import br.com.agenda.dto.response.cidade.CriarCidadeResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.validator.cidade.CriarCidadeRequestValidator;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.mapper.cidade.CriarCidadeRequestMapper;
import br.com.agenda.repository.CidadeRepository;

@RunWith(MockitoJUnitRunner.class)
public class CriarCidadeServiceTest {

    @InjectMocks
    private CriarCidadeService criarCidadeService;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private CriarCidadeRequestValidator criarCidadeRequestValidator;

    @Mock
    private CriarCidadeRequestMapper criarCidadeRequestMapper;

    @Captor
    ArgumentCaptor<CidadeEntity> cidadeEntityArgumentCaptor;

    @Test
    @DisplayName("Deve Criar Uma Cidade Corretamente")
    public void DeveCriarUmaCidadeCorretamente(){
        final CriarCidadeRequest request = make(CriarCidadeRequest.builder())
            .build();

        final CidadeEntity cidade = Fixture.make(CidadeEntity.builder())
            .build();

        when(criarCidadeRequestMapper.apply(request)).thenReturn(cidade);

        CriarCidadeResponse response = criarCidadeService.criar(request);

        verify(criarCidadeRequestValidator).accept(request);
        verify(criarCidadeRequestMapper).apply(request);
        verify(cidadeRepository).save(cidadeEntityArgumentCaptor.capture());

        assertEquals(cidadeEntityArgumentCaptor.getValue().getId(), response.getId());
    }
}
