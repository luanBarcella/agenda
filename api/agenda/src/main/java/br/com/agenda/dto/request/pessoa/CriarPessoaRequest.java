package br.com.agenda.dto.request.pessoa;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class CriarPessoaRequest implements Serializable {

    private static final long serialVersionUID = 9047247700215923030L;

    @NotBlank(message = "{nome.invalido}")
    private final String nome;

    @NotNull(message = "{idade.invalida}")
    private final Integer idade;

    @Email(message = "{email.invalido}")
    @NotNull(message = "{email.invalido}")
    private final String email;

    @NotNull(message = "{cidade.id-nao-informado}")
    private final Long idCidade;

    private final String linkImagem;

}
