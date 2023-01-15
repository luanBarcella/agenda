package br.com.agenda.service.pessoa;

import org.springframework.stereotype.Service;

import br.com.agenda.mapper.pessoa.ConsultarPessoaPorIdResponseMapper;
import br.com.agenda.service.support.PegarPessoaService;
import br.com.agenda.dto.response.pessoa.ConsultarPessoaPorIdResponse;
import br.com.agenda.model.PessoaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultarPessoaPorIdService {

    private final PegarPessoaService pegarPessoaService;

    private static final ConsultarPessoaPorIdResponseMapper CONSULTAR_PESSOA_ID_RESPONSE_MAPPER = new ConsultarPessoaPorIdResponseMapper();

    public ConsultarPessoaPorIdResponse consultar(final Long pessoaId){
        log.info("Consultando pessoa de id: {}", pessoaId);
        final PessoaEntity pessoa = pegarPessoaService.porId(pessoaId);

        return CONSULTAR_PESSOA_ID_RESPONSE_MAPPER.apply(pessoa);
    }
}
