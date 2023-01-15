package br.com.agenda.dto.request.local;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class EditarLocalRequest implements Serializable {

    private static final long serialVersionUID = 2485070647613987362L;

    private final String nome;
    private final String linkImagem;
}
