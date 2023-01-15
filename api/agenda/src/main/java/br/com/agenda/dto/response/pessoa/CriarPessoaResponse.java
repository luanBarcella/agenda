package br.com.agenda.dto.response.pessoa;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class CriarPessoaResponse implements Serializable {

    private static final long serialVersionUID = -6676861096707736603L;

    private final Long idPessoa;

}
