package br.com.agenda.dto.response.local;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class EditarLocalResponse implements Serializable {

    private static final long serialVersionUID = -6954812432763405554L;

    private final String mensagem;
    private final List<String> camposAlterados;
}
