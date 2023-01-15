package br.com.agenda.dto.response.cidade;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ConsultarCidadePorIdLocalResponse implements Serializable {

    private static final long serialVersionUID = 7013997038864306651L;

    private final Long id;
    private final String nome;
    private final String linkImagem;
}
