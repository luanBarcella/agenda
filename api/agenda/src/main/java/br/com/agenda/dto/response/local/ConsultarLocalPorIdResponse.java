package br.com.agenda.dto.response.local;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ConsultarLocalPorIdResponse implements Serializable {

    private static final long serialVersionUID = -3901354014725364667L;

    private final Long id;
    private final String nome;
    private final String linkImagem;
    private final ConsultarLocalPorIdCidadeResponse cidade;
}
