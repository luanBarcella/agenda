package br.com.agenda.service.local;

import static br.com.agenda.fixture.Fixture.make;
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

import br.com.agenda.dto.response.local.ConsultarLocalResponse;
import br.com.agenda.mapper.local.ConsultarLocalResponseMapper;
import br.com.agenda.repository.LocalRepository;
import br.com.agenda.model.LocalEntity;

@RunWith(MockitoJUnitRunner.class)
public class ConsultarLocalServiceTest {

    private static final ConsultarLocalResponseMapper CONSULTAR_LOCAIS_RESPONSE_MAPPER = new ConsultarLocalResponseMapper();

    @InjectMocks
    private ConsultarLocalService consultarLocalService;

    @Mock
    private LocalRepository localRepository;

    @Test
    @DisplayName("Deve Retornar Page Com Lista de Locais Corretamente")
    public void DeveRetornarPageComListaDeLocaisCorretamente(){
        final Pageable pageable = PageRequest.of(0, 2);

        final LocalEntity local = make(LocalEntity.builder())
            .build();

        final List<LocalEntity> locais = asList(local, local, local);

        when(localRepository.findAll(pageable)).thenReturn(new PageImpl<>(locais));

        final Page<ConsultarLocalResponse> locaisresponse = consultarLocalService.consultar(pageable);

        verify(localRepository).findAll(pageable);

        assertNotNull(locaisresponse);
        assertEquals(locais.size(), locaisresponse.getContent().size());

    }

}
