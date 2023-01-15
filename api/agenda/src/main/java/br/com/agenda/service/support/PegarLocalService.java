package br.com.agenda.service.support;

import org.springframework.stereotype.Service;

import br.com.agenda.domain.ErrorType;
import br.com.agenda.domain.Message;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.repository.LocalRepository;
import br.com.agenda.exception.ClientErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PegarLocalService {

    private final LocalRepository localRepository;
    private final MessageService messageService;

    public LocalEntity porId(final Long localId){
        return localRepository.findById(localId)
            .orElseThrow(() -> new ClientErrorException(
                ErrorType.VALIDATION,
                messageService.get(Message.LOCAL_ID_INVALIDO)));
    }
}
