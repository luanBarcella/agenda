package br.com.agenda.service.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.agenda.dto.response.pessoa.ConsultarPessoaResponse;
import br.com.agenda.mapper.pessoa.ConsultarPessoaResponseMapper;
import br.com.agenda.model.PessoaEntity;
import br.com.agenda.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultarPessoaService {

    private final PessoaRepository pessoaRepository;

    private static final ConsultarPessoaResponseMapper CONSULTAR_PESSOA_RESPONSE_MAPPER = new ConsultarPessoaResponseMapper();

    public Page<ConsultarPessoaResponse> consultar(final Pageable pageable){
        log.info("Consultando pessoas para page: {}", pageable);
        final Page<PessoaEntity> pessoas = pessoaRepository.findAll(pageable);

        return pessoas.map(CONSULTAR_PESSOA_RESPONSE_MAPPER::apply);
    }

}
