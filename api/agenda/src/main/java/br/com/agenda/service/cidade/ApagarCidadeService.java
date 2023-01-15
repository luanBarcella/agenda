package br.com.agenda.service.cidade;

import static br.com.agenda.domain.Message.CIDADE_APAGADA_SUCESSO;

import org.springframework.stereotype.Service;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.dto.response.cidade.ApagarCidadeResponse;
import br.com.agenda.repository.CidadeRepository;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.service.support.PegarCidadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApagarCidadeService {

    private final PegarCidadeService pegarCidadeService;
    private final CidadeRepository cidadeRepository;
    private final MessageService messageService;

    public ApagarCidadeResponse apagar(final Long idCidade){
        log.info("iniciando o processo de exclusão da cidade com id: {}", idCidade);
        final CidadeEntity cidade = pegarCidadeService.porId(idCidade);

        cidadeRepository.delete(cidade);

        return ApagarCidadeResponse.builder()
            .mensagem(messageService.get(CIDADE_APAGADA_SUCESSO))
            .build();
    }
}
