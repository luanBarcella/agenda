package br.com.agenda.mapper.pessoa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import br.com.agenda.dto.response.pessoa.ConsultarPessoaPorIdLocalResponse;
import br.com.agenda.dto.response.pessoa.ConsultarPessoaPorIdCidadeResponse;
import br.com.agenda.dto.response.pessoa.ConsultarPessoaPorIdResponse;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.model.PessoaEntity;

public class ConsultarPessoaPorIdResponseMapperTest {

    private static final ConsultarPessoaPorIdResponseMapper CONSULTAR_PESSOA_ID_RESPONSE_MAPPER = new ConsultarPessoaPorIdResponseMapper();

    @Test
    @DisplayName("Deve Transformar PessoaEntity em ConsultarPessoaIdResponse Corretamente")
    public void DeveTransformarPessoaEntityEmConsultarPessoaIdResponseCorretamente(){
        final PessoaEntity pessoa = Fixture.make(PessoaEntity.builder())
            .build();

        final ConsultarPessoaPorIdResponse response = CONSULTAR_PESSOA_ID_RESPONSE_MAPPER.apply(pessoa);

        assertConsultarPessoaIdResponse(pessoa, response);
    }

    private void assertConsultarPessoaIdResponse(final PessoaEntity expected, final ConsultarPessoaPorIdResponse response){
        assertNotNull(response);
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getNome(), response.getNome());
        assertEquals(expected.getIdade(), response.getIdade());
        assertEquals(expected.getLinkImagem(), response.getLinkImagem());
        assertEquals(expected.getEmail(), response.getEmail());
        assertConsultarPessoaIdCidadeResponse(expected.getCidade(), response.getCidade());
    }

    private void assertConsultarPessoaIdCidadeResponse(final CidadeEntity expected, final ConsultarPessoaPorIdCidadeResponse response){
        assertNotNull(response);
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getNome(), response.getNome());
        assertEquals(expected.getUF(), response.getUF());
        assertEquals(expected.getLinkImagem(), response.getLinkImagem());
        assertConsultarPessoaIdLocalResponses(expected.getLocais(), response.getLocais());
    }

    private void assertConsultarPessoaIdLocalResponses(final List<LocalEntity> expected, final List<ConsultarPessoaPorIdLocalResponse> response){
        assertNotNull(response);
        assertEquals(expected.size(), response.size());
        for(int i = 0; i < expected.size(); i++){
            assertEquals(expected.get(i).getId(), response.get(i).getId());
            assertEquals(expected.get(i).getNome(), response.get(i).getNome());
            assertEquals(expected.get(i).getLinkImagem(), response.get(i).getLinkImagem());
        }
    }

}
