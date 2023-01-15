package br.com.agenda.dto.response.pessoa;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class ConsultarPessoaCidadeResponse implements Serializable {

    private static final long serialVersionUID = 3351739946695173581L;

    private final Long id;
    private final String nome;
    private final String UF;
    private final String linkImagem;
}
