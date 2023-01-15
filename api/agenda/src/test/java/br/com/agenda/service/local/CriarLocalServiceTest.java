package br.com.agenda.service.local;

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
import br.com.agenda.repository.LocalRepository;
import br.com.agenda.validator.local.CriarLocalRequestValidator;
import br.com.agenda.dto.request.local.CriarLocalRequest;
import br.com.agenda.dto.response.local.CriarLocalResponse;
import br.com.agenda.mapper.local.CriarLocalRequestMapper;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.service.support.PegarCidadeService;

@RunWith(MockitoJUnitRunner.class)
public class CriarLocalServiceTest {

    @InjectMocks
    private  CriarLocalService criarLocalService;

    @Mock
    private PegarCidadeService pegarCidadeService;

    @Mock
    private LocalRepository localRepository;

    @Mock
    private CriarLocalRequestMapper criarLocalRequestMapper;

    @Mock
    private CriarLocalRequestValidator criarLocalRequestValidator;

    @Captor
    private ArgumentCaptor<LocalEntity> localEntityArgumentCaptor;

    @Test
    @DisplayName("Deve Criar Um Local Corretamente")
    public void DeveCriarUmLocalCorretamente(){
        final CriarLocalRequest request = make(CriarLocalRequest.builder()).build();
        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        final LocalEntity local = make(LocalEntity.builder())
            .build();

        when(pegarCidadeService.porId(request.getIdCidade())).thenReturn(cidade);
        when(criarLocalRequestMapper.apply(request, cidade)).thenReturn(local);

        final CriarLocalResponse response = criarLocalService.criar(request);

        verify(criarLocalRequestValidator).accept(request);
        verify(pegarCidadeService).porId(request.getIdCidade());
        verify(criarLocalRequestMapper).apply(request, cidade);
        verify(localRepository).save(localEntityArgumentCaptor.capture());

        final LocalEntity localCaptor = localEntityArgumentCaptor.getValue();

        assertNotNull(response);
        assertTrue(cidade.getLocais().contains(localCaptor));
        assertEquals(localCaptor.getId(), response.getId());
    }

}
