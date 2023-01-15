package br.com.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.agenda.model.CidadeEntity;

public interface CidadeRepository extends JpaRepository<CidadeEntity, Long> {

    boolean existsByNomeAndUFAndLinkImagem(final String nome, final String UF, final String linkImagem);

}
