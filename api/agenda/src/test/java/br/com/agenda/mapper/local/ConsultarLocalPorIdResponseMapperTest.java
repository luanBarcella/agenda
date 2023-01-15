package br.com.agenda.mapper.local;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import br.com.agenda.dto.response.local.ConsultarLocalPorIdCidadeResponse;
import br.com.agenda.dto.response.local.ConsultarLocalPorIdResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.LocalEntity;

public class ConsultarLocalPorIdResponseMapperTest {

    private static final ConsultarLocalPorIdResponseMapper CONSULTAR_LOCAL_POR_ID_RESPONSE_MAPPER = new ConsultarLocalPorIdResponseMapper();

    @Test
    @DisplayName("Deve Transformar LocalEntity em ConsultarLocalPorIdResponseCorretamente")
    public void DeveTransformarLocalEntityEmConsultarLocalPorIdResponseCorretamente(){
        final LocalEntity local = Fixture.make(LocalEntity.builder())
            .build();

        final ConsultarLocalPorIdResponse response = CONSULTAR_LOCAL_POR_ID_RESPONSE_MAPPER.apply(local);

        assertConsultarLocalPorIdResponse(local, response);

    }

    private void assertConsultarLocalPorIdResponse(
        final LocalEntity local,
        final ConsultarLocalPorIdResponse response){

        assertNotNull(response);
        assertEquals(local.getId(), response.getId());
        assertEquals(local.getNome(), response.getNome());
        assertEquals(local.getLinkImagem(), response.getLinkImagem());
        assertConsultarLocalPorIdCidadeResponse(local.getCidade(), response.getCidade());

    }

    private void assertConsultarLocalPorIdCidadeResponse(
        final CidadeEntity cidade,
        final ConsultarLocalPorIdCidadeResponse response){

        assertNotNull(response);
        assertEquals(cidade.getId(), response.getId());
        assertEquals(cidade.getLinkImagem(),response.getLinkImagem());
        assertEquals(cidade.getNome(), response.getNome());
        assertEquals(cidade.getUF(), response.getUF());

    }

}
