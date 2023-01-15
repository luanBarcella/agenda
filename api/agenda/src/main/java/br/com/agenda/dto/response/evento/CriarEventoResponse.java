package br.com.agenda.dto.response.evento;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class CriarEventoResponse implements Serializable {

    private static final long serialVersionUID = -4949041402714921548L;

    private final Long eventoId;

}
