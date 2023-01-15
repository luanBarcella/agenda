package br.com.agenda.dto.request.local;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class CriarLocalRequest implements Serializable {

    private static final long serialVersionUID = 3327595626097239215L;

    @NotBlank(message = "{nome.invalido}")
    private final String nome;

    @NotNull(message = "{cidade.id-nao-informado}")
    private final Long idCidade;

    private final String linkImagem;

}
