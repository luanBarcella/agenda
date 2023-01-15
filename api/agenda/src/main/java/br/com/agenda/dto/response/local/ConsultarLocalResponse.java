package br.com.agenda.dto.response.local;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ConsultarLocalResponse implements Serializable {

    private static final long serialVersionUID = 5486123678769065821L;

    private final Long id;
    private final String nome;
    private final String linkImagem;
    private final ConsultarLocalCidadeResponse cidade;
}
