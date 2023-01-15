package br.com.agenda.mapper.pessoa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import br.com.agenda.dto.response.pessoa.ConsultarPessoaCidadeResponse;
import br.com.agenda.dto.response.pessoa.ConsultarPessoaResponse;
import br.com.agenda.fixture.Fixture;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.PessoaEntity;

public class ConsultarPessoaResponseMapperTest {

    private static final ConsultarPessoaResponseMapper CONSULTAR_PESSOA_RESPONSE_MAPPER = new ConsultarPessoaResponseMapper();

    @Test
    @DisplayName("Deve Transformar PessoaEntity em ConsultarPessoaResponse Corretamente")
    public void DeveTransformarPessoaEntityEmConsultarPessoaResponseCorretamente(){
        final PessoaEntity pessoa = Fixture.make(PessoaEntity.builder()).build();

        final ConsultarPessoaResponse response = CONSULTAR_PESSOA_RESPONSE_MAPPER.apply(pessoa);

        assertConsultarPessoaResponse(pessoa, response);
    }

    private void assertConsultarPessoaResponse(final PessoaEntity expected, final ConsultarPessoaResponse response){
        assertNotNull(response);
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getNome(), response.getNome());
        assertEquals(expected.getIdade(), response.getIdade());
        assertEquals(expected.getLinkImagem(), response.getLinkImagem());
        assertEquals(expected.getEmail(), response.getEmail());
        assertConsultarPessoaCidadeResponse(expected.getCidade(), response.getCidade());
    }

    private void assertConsultarPessoaCidadeResponse(final CidadeEntity expected, final ConsultarPessoaCidadeResponse response){
        assertNotNull(response);
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getNome(), response.getNome());
        assertEquals(expected.getUF(), response.getUF());
        assertEquals(expected.getLinkImagem(), response.getLinkImagem());
    }

}
