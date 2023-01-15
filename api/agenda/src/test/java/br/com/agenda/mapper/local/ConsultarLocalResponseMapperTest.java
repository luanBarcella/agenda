package br.com.agenda.mapper.local;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.dto.response.local.ConsultarLocalCidadeResponse;
import br.com.agenda.dto.response.local.ConsultarLocalResponse;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.LocalEntity;

public class ConsultarLocalResponseMapperTest {

    private static final ConsultarLocalResponseMapper CONSULTAR_LOCAIS_RESPONSE_MAPPER = new ConsultarLocalResponseMapper();

    @Test
    @DisplayName("Deve Transformar LocalEntity em ConsultarLocaisResponse Corretamente")
    public void DeveTransformarLocalEntityEmConsultarLocaisResponseCorretamente(){
        final LocalEntity local = Fixture.make(LocalEntity.builder())
            .build();

        final ConsultarLocalResponse localResponse = CONSULTAR_LOCAIS_RESPONSE_MAPPER.apply(local);

        assertConsultarLocaisResponse(local, localResponse);

    }

    private void assertConsultarLocaisResponse(
        final LocalEntity local,
        final ConsultarLocalResponse response){

        assertNotNull(response);
        assertEquals(local.getId(), response.getId());
        assertEquals(local.getNome(), response.getNome());
        assertEquals(local.getLinkImagem(), response.getLinkImagem());
        assertConsultarLocaisCidadeResponse(local.getCidade(), response.getCidade());
    }

    private void assertConsultarLocaisCidadeResponse(
        final CidadeEntity cidade,
        final ConsultarLocalCidadeResponse response){

        assertNotNull(response);
        assertEquals(cidade.getId(), response.getId());
        assertEquals(cidade.getNome(), response.getNome());
        assertEquals(cidade.getUF(), response.getUF());
        assertEquals(cidade.getLinkImagem(), response.getLinkImagem());
    }
}
