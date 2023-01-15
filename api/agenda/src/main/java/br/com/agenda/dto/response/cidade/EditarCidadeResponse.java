package br.com.agenda.dto.response.cidade;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class EditarCidadeResponse implements Serializable {

    private static final long serialVersionUID = 5013892419131101973L;

    private final String mensagem;
    private final List<String> camposAlterados;

}
