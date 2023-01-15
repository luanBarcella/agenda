package br.com.agenda.mapper.cidade;

import java.util.function.Function;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.dto.response.cidade.ConsultarCidadeResponse;

public class ConsultarCidadeResponseMapper
    implements Function<CidadeEntity, ConsultarCidadeResponse> {


    @Override
    public ConsultarCidadeResponse apply(final CidadeEntity cidadeEntity) {
        return ConsultarCidadeResponse.builder()
            .id(cidadeEntity.getId())
            .nome(cidadeEntity.getNome())
            .UF(cidadeEntity.getUF())
            .linkImagem(cidadeEntity.getLinkImagem())
            .build();
    }
}
