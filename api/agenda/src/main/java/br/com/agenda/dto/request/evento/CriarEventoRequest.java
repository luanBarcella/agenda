package br.com.agenda.dto.request.evento;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.agenda.enums.Categoria;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class CriarEventoRequest implements Serializable {

    private static final long serialVersionUID = -6771492688051955068L;

    @NotBlank(message = "{titulo.invalido}")
    private final String titulo;

    @NotNull(message = "{data-inicio.invalida}")
    private final OffsetDateTime dataInicio;

    @NotNull(message = "{data-fim.invalida}")
    private final OffsetDateTime dataFim;

    @NotNull(message = "{categoria.invalida}")
    private final Categoria categoria;

    @NotNull(message = "{local.id-invalido}")
    private final Long idLocal;

    private final String linkImagem;

}
