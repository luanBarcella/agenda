package br.com.agenda.dto.response.cidade;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class CriarCidadeResponse implements Serializable {

    private static final long serialVersionUID = 2823538873154835796L;

    private final Long id;
}
