package br.com.agenda.dto.response.local;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ConsultarLocalCidadeResponse implements Serializable {

    private static final long serialVersionUID = -6847508300260100371L;

    private final Long id;
    private final String nome;
    private final String UF;
    private final String linkImagem;
}
