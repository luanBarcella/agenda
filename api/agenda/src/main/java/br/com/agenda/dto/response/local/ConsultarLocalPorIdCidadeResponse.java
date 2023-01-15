package br.com.agenda.dto.response.local;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ConsultarLocalPorIdCidadeResponse implements Serializable {

    private static final long serialVersionUID = -6417072822441822419L;

    private final Long id;
    private final String nome;
    private final String UF;
    private final String linkImagem;
}
