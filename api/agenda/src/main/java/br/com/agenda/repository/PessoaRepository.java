package br.com.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.agenda.model.PessoaEntity;

public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {

    boolean existsByEmail(final String email);
}
