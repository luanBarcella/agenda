package br.com.agenda.dto.response.cidade;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ConsultarCidadePorIdResponse implements Serializable {

    private static final long serialVersionUID = -3864972824980777360L;

    private final Long id;
    private final String nome;
    private final String UF;
    private final String linkImagem;
    private final List<ConsultarCidadePorIdLocalResponse> locais;

}
