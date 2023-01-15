package br.com.agenda.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.agenda.enums.Categoria;
import br.com.agenda.model.EventoEntity;
import br.com.agenda.model.LocalEntity;

public interface EventoRepository extends JpaRepository<EventoEntity, Long> {

    boolean existsByTituloAndDataInicioAndDataFimAndLinkImagemAndCategoriaAndLocalEvento(
        final String titulo, final OffsetDateTime dataInicio, final OffsetDateTime dataFim, final String linkImagem, final Categoria categoria, final LocalEntity localEvento);
}
