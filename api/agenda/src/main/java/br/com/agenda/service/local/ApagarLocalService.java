package br.com.agenda.service.local;

import org.springframework.stereotype.Service;

import br.com.agenda.domain.Message;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.repository.LocalRepository;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.service.support.PegarLocalService;
import br.com.agenda.dto.response.local.ApagarLocalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApagarLocalService {

    private final PegarLocalService pegarLocalService;
    private final LocalRepository localRepository;
    private final MessageService messageService;

    public ApagarLocalResponse apagar(final Long localId){
        log.info("Iniciando o processo de exclusão do local de id: {}", localId);
        final LocalEntity local = pegarLocalService.porId(localId);

        localRepository.delete(local);

        return ApagarLocalResponse.builder()
            .mensagem(messageService.get(Message.LOCAL_APAGADO_SUCESSO))
            .build();
    }

}
