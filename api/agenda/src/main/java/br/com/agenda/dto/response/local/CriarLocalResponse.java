package br.com.agenda.dto.response.local;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class CriarLocalResponse implements Serializable {

    private static final long serialVersionUID = -2923450473920720355L;

    private final Long id;
}
