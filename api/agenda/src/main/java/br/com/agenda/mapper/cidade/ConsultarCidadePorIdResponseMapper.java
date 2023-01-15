package br.com.agenda.mapper.cidade;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.agenda.dto.response.cidade.ConsultarCidadePorIdLocalResponse;
import br.com.agenda.dto.response.cidade.ConsultarCidadePorIdResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.LocalEntity;

public class ConsultarCidadePorIdResponseMapper
    implements Function<CidadeEntity, ConsultarCidadePorIdResponse> {

    @Override
    public ConsultarCidadePorIdResponse apply(final CidadeEntity cidadeEntity) {
        return ConsultarCidadePorIdResponse.builder()
            .id(cidadeEntity.getId())
            .UF(cidadeEntity.getUF())
            .nome(cidadeEntity.getNome())
            .linkImagem(cidadeEntity.getLinkImagem())
            .locais(converterLocaisResponse(cidadeEntity.getLocais()))
            .build();
    }

    private List<ConsultarCidadePorIdLocalResponse> converterLocaisResponse(final List<LocalEntity> locais){
        return locais.stream()
            .map((local) ->
                ConsultarCidadePorIdLocalResponse.builder()
                    .id(local.getId())
                    .nome(local.getNome())
                    .linkImagem(local.getLinkImagem())
                    .build()
            ).collect(Collectors.toList());
    }
}
