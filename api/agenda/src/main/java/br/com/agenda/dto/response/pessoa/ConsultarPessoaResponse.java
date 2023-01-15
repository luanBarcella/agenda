package br.com.agenda.dto.response.pessoa;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class ConsultarPessoaResponse implements Serializable {

    private static final long serialVersionUID = 7095116789299583786L;

    private final Long id;
    private final String nome;
    private final Integer idade;
    private final String linkImagem;
    private final String email;
    private final ConsultarPessoaCidadeResponse cidade;
}
