package br.com.agenda.service.local;

import org.springframework.stereotype.Service;

import br.com.agenda.dto.response.local.ConsultarLocalPorIdResponse;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.mapper.local.ConsultarLocalPorIdResponseMapper;
import br.com.agenda.service.support.PegarLocalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultarLocalPorIdService {

    private static final ConsultarLocalPorIdResponseMapper CONSULTAR_LOCAL_POR_ID_RESPONSE_MAPPER = new ConsultarLocalPorIdResponseMapper();

    private final PegarLocalService pegarLocalService;

    public ConsultarLocalPorIdResponse consultar(final Long localId){
        log.info("Consultando local com id: {}", localId);
        final LocalEntity local = pegarLocalService.porId(localId);

        return CONSULTAR_LOCAL_POR_ID_RESPONSE_MAPPER.apply(local);
    }

}
