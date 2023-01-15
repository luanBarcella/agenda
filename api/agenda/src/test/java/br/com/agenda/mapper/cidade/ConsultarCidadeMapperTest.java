package br.com.agenda.mapper.cidade;

import static br.com.agenda.fixture.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.dto.response.cidade.ConsultarCidadeResponse;

public class ConsultarCidadeMapperTest {

    private static final ConsultarCidadeResponseMapper CONSULTAR_CIDADE_RESPONSE_MAPPER = new ConsultarCidadeResponseMapper();

    @Test
    @DisplayName("Deve Transformar CidadeEntity em ConsultarCidadeResponse Corretamente")
    public void DeveTransformarCidadeEntityEmConsultarCidadeResponseCorretamente(){
        final CidadeEntity cidade = make(CidadeEntity.builder()).build();

        final ConsultarCidadeResponse response = CONSULTAR_CIDADE_RESPONSE_MAPPER.apply(cidade);

        assertEquals(cidade.getId(), response.getId());
        assertEquals(cidade.getNome(), response.getNome());
        assertEquals(cidade.getLinkImagem(), response.getLinkImagem());
        assertEquals(cidade.getUF(), response.getUF());

    }

}
