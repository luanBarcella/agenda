package br.com.agenda.dto.response.pessoa;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class EditarPessoaResponse implements Serializable {

    private static final long serialVersionUID = -7797297542092470606L;

    private final String mensagem;
    private final List<String> camposAlterados;
}
