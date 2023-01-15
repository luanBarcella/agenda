package br.com.agenda.mapper.local;

import java.util.function.Function;

import br.com.agenda.dto.response.local.ConsultarLocalPorIdCidadeResponse;
import br.com.agenda.dto.response.local.ConsultarLocalPorIdResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.LocalEntity;

public class ConsultarLocalPorIdResponseMapper
    implements Function<LocalEntity, ConsultarLocalPorIdResponse> {

    @Override
    public ConsultarLocalPorIdResponse apply(final LocalEntity localEntity) {
        return ConsultarLocalPorIdResponse.builder()
            .id(localEntity.getId())
            .nome(localEntity.getNome())
            .linkImagem(localEntity.getLinkImagem())
            .cidade(cidadeEntityToResponse(localEntity.getCidade()))
            .build();
    }

    private ConsultarLocalPorIdCidadeResponse cidadeEntityToResponse(final CidadeEntity cidade){
        return ConsultarLocalPorIdCidadeResponse.builder()
            .id(cidade.getId())
            .nome(cidade.getNome())
            .UF(cidade.getUF())
            .linkImagem(cidade.getLinkImagem())
            .build();
    }
}
