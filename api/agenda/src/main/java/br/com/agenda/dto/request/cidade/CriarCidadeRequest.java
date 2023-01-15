package br.com.agenda.dto.request.cidade;


import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CriarCidadeRequest implements Serializable {

    private static final long serialVersionUID = 2877506726030387241L;

    @NotBlank(message = "{nome.invalido}")
    private final String nome;

    @NotBlank(message = "{uf.invalida}")
    private final String UF;

    private final String linkImagem;

}
