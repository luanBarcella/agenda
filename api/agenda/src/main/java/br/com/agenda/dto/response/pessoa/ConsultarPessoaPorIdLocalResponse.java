package br.com.agenda.dto.response.pessoa;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ConsultarPessoaPorIdLocalResponse implements Serializable {

    private static final long serialVersionUID = -7827587965669043869L;

    private final Long id;
    private final String nome;
    private final String linkImagem;
}
