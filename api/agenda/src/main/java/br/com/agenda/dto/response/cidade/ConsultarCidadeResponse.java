package br.com.agenda.dto.response.cidade;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ConsultarCidadeResponse implements Serializable {

    private static final long serialVersionUID = -1408889253629310970L;

    private final Long id;
    private final String nome;
    private final String UF;
    private final String linkImagem;

}
