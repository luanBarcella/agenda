package br.com.agenda.mapper.cidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import br.com.agenda.dto.response.cidade.ConsultarCidadePorIdLocalResponse;
import br.com.agenda.dto.response.cidade.ConsultarCidadePorIdResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.LocalEntity;

public class ConsultarCidadePorIdResponseMapperTest {

    private static final ConsultarCidadePorIdResponseMapper CONSULTAR_CIDADE_POR_ID_RESPONSE_MAPPER = new ConsultarCidadePorIdResponseMapper();

    @Test
    @DisplayName("Deve Transformar CidadeEntity em ConsultarCidadePorIdResponse Corretamente Sem Locais")
    public void DeveTransformarCidadeEntityEmConsultarCidadePorIdResponseCorretamenteSemLocais(){
        final CidadeEntity cidade = Fixture.make(CidadeEntity.builder())
            .locais(new ArrayList<>())
            .build();

        ConsultarCidadePorIdResponse response = CONSULTAR_CIDADE_POR_ID_RESPONSE_MAPPER.apply(cidade);

        assertNotNull(response);
        assertEquals(cidade.getId(), response.getId());
        assertEquals(cidade.getUF(), response.getUF());
        assertEquals(cidade.getNome(), response.getNome());
        assertEquals(cidade.getLinkImagem(), response.getLinkImagem());
        assertTrue(cidade.getLocais().isEmpty());

    }

    @Test
    @DisplayName("Deve Transformar CidadeEntity em ConsultarCidadePorIdResponse Corretamente Com Locais")
    public void DeveTransformarCidadeEntityEmConsultarCidadePorIdResponseCorretamenteComLocais(){
        final CidadeEntity cidade = Fixture.make(CidadeEntity.builder())
            .build();

        ConsultarCidadePorIdResponse response = CONSULTAR_CIDADE_POR_ID_RESPONSE_MAPPER.apply(cidade);

        assertNotNull(response);
        assertEquals(cidade.getId(), response.getId());
        assertEquals(cidade.getUF(), response.getUF());
        assertEquals(cidade.getNome(), response.getNome());
        assertEquals(cidade.getLinkImagem(), response.getLinkImagem());
        assertLocais(cidade.getLocais(), response.getLocais());
    }

    private void assertLocais(
        final List<LocalEntity> locaisCidade,
        final List<ConsultarCidadePorIdLocalResponse> locaisResponse){

        assertEquals(locaisCidade.size(), locaisResponse.size());

        for(int i = 0; i < locaisCidade.size(); i++){
            assertEquals(locaisCidade.get(i).getId(), locaisResponse.get(i).getId());
            assertEquals(locaisCidade.get(i).getNome(), locaisResponse.get(i).getNome());
            assertEquals(locaisCidade.get(i).getLinkImagem(), locaisResponse.get(i).getLinkImagem());
        }
    }
}
