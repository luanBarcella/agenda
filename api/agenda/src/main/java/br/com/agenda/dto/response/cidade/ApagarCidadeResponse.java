package br.com.agenda.dto.response.cidade;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ApagarCidadeResponse implements Serializable {

    private static final long serialVersionUID = 589822306994325486L;

    private final String mensagem;

}
