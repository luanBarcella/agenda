package br.com.agenda.service.local;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.agenda.dto.response.local.ConsultarLocalResponse;
import br.com.agenda.mapper.local.ConsultarLocalResponseMapper;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.repository.LocalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultarLocalService {

    private static final ConsultarLocalResponseMapper CONSULTAR_LOCAIS_RESPONSE_MAPPER = new ConsultarLocalResponseMapper();

    private final LocalRepository localRepository;

    public Page<ConsultarLocalResponse> consultar(final Pageable pageable){
        log.info("Consultando locais para page: {}", pageable);
        final Page<LocalEntity> locais = localRepository.findAll(pageable);

        return locais.map(CONSULTAR_LOCAIS_RESPONSE_MAPPER::apply);
    }

}
