package br.com.agenda.service.cidade;

import org.springframework.stereotype.Service;

import br.com.agenda.dto.response.cidade.ConsultarCidadePorIdResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.service.support.PegarCidadeService;
import br.com.agenda.mapper.cidade.ConsultarCidadePorIdResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultarCidadePorIdService {

    private static final ConsultarCidadePorIdResponseMapper CONSULTAR_CIDADE_POR_ID_RESPONSE_MAPPER = new ConsultarCidadePorIdResponseMapper();

    private final PegarCidadeService pegarCidadeService;

    public ConsultarCidadePorIdResponse consultar(final Long idCidade){
        log.info("Consultando cidade com o ID: {}", idCidade);
        final CidadeEntity cidade = pegarCidadeService.porId(idCidade);

        return CONSULTAR_CIDADE_POR_ID_RESPONSE_MAPPER.apply(cidade);
    }
}
