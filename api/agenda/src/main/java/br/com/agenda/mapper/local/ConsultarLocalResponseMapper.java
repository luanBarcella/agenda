package br.com.agenda.mapper.local;

import java.util.function.Function;

import br.com.agenda.dto.response.local.ConsultarLocalCidadeResponse;
import br.com.agenda.dto.response.local.ConsultarLocalResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.LocalEntity;

public class ConsultarLocalResponseMapper
    implements Function <LocalEntity, ConsultarLocalResponse> {

    @Override
    public ConsultarLocalResponse apply(final LocalEntity localEntity) {
        return ConsultarLocalResponse.builder()
            .id(localEntity.getId())
            .nome(localEntity.getNome())
            .linkImagem(localEntity.getLinkImagem())
            .cidade(cidadeEntityToResponse(localEntity.getCidade()))
            .build();
    }

    private ConsultarLocalCidadeResponse cidadeEntityToResponse(final CidadeEntity cidade){
        return ConsultarLocalCidadeResponse.builder()
            .id(cidade.getId())
            .nome(cidade.getNome())
            .UF(cidade.getUF())
            .linkImagem(cidade.getLinkImagem())
            .build();
    }
}
