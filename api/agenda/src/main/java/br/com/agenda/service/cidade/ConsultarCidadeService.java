package br.com.agenda.service.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.agenda.dto.response.cidade.ConsultarCidadeResponse;
import br.com.agenda.mapper.cidade.ConsultarCidadeResponseMapper;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultarCidadeService {

    private static final ConsultarCidadeResponseMapper CONSULTAR_CIDADE_RESPONSE_MAPPER = new ConsultarCidadeResponseMapper();

    private final CidadeRepository cidadeRepository;

    public Page<ConsultarCidadeResponse> consultar(final Pageable pageable){
        log.info("Consultando cidades para page: {}", pageable);
        Page<CidadeEntity> cidades = cidadeRepository.findAll(pageable);

        return cidades.map(CONSULTAR_CIDADE_RESPONSE_MAPPER::apply);
    }

}
