package br.com.agenda.dto.response.pessoa;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ConsultarPessoaPorIdResponse implements Serializable {

    private static final long serialVersionUID = -3393668714941822210L;

    private final Long id;
    private final String nome;
    private final Integer idade;
    private final String linkImagem;
    private final String email;
    private final ConsultarPessoaPorIdCidadeResponse cidade;
}
