package br.com.agenda.dto.response.pessoa;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ConsultarPessoaPorIdCidadeResponse implements Serializable {

    private static final long serialVersionUID = -6764069740635981792L;

    private final Long id;
    private final String nome;
    private final String UF;
    private final String linkImagem;
    private final List<ConsultarPessoaPorIdLocalResponse> locais;
}
