package br.com.agenda.dto.response.local;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ApagarLocalResponse implements Serializable {

    private static final long serialVersionUID = 8237497806862478150L;

    private final String mensagem;
}
