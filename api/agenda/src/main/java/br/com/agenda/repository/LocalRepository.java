package br.com.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.LocalEntity;

public interface LocalRepository extends JpaRepository<LocalEntity, Long> {

    boolean existsByNomeAndLinkImagemAndCidade(final String nome, final String linkImagem, final CidadeEntity cidade);

    boolean existsByNomeAndLinkImagem(final String nome, final String linkImagem);
}
