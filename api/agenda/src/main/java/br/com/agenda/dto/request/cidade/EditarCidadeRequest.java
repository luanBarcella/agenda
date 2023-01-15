package br.com.agenda.dto.request.cidade;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class EditarCidadeRequest implements Serializable {

    private static final long serialVersionUID = -310359533978354560L;

    private final String nome;
    private final String UF;
    private final String linkImagem;

}
