package br.com.agenda.dto.request.pessoa;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class EditarPessoaRequest implements Serializable {

    private static final long serialVersionUID = 2379590376171105881L;

    private String nome;
    private Integer idade;
    private String linkImagem;
    private String email;
    private Long cidadeId;
}
