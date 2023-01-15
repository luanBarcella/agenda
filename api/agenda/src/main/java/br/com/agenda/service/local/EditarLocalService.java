package br.com.agenda.service.local;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.agenda.domain.Message;
import br.com.agenda.dto.request.local.EditarLocalRequest;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.repository.LocalRepository;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.service.support.PegarLocalService;
import br.com.agenda.validator.local.EditarLocalRequestValidator;
import br.com.agenda.dto.response.local.EditarLocalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EditarLocalService {

    private final PegarLocalService pegarLocalService;
    private final MessageService messageService;
    private final LocalRepository localRepository;
    private final EditarLocalRequestValidator editarLocalRequestValidator;

    public EditarLocalResponse editar(final EditarLocalRequest request, final Long localId){
        log.info("Iniciando processo de edição do local: {}", localId);
        List<String> camposAlterados = new ArrayList<>();

        LocalEntity local = pegarLocalService.porId(localId);

        editarLocalRequestValidator.accept(request, local);

        if(nonNull(request.getNome()) && isNotBlank(request.getNome())){
            local.setNome(request.getNome());
            camposAlterados.add(messageService.get(Message.NOME_ALTERADO_SUCESSO));
        }

        if(nonNull(request.getLinkImagem()) && isNotBlank(request.getLinkImagem())){
            local.setLinkImagem(request.getLinkImagem());
            camposAlterados.add(messageService.get(Message.LINK_IMAGEM_ALTERADO_SUCESSO));
        }

        localRepository.save(local);

        final String mensagem = camposAlterados.size() == 0 ?
            messageService.get(Message.NENHUM_CAMPO_ALTERADO) :
            messageService.get(Message.CAMPOS_ALTERADOS_SUCESSO) + camposAlterados.size();

        return EditarLocalResponse.builder()
            .mensagem(mensagem)
            .camposAlterados(camposAlterados)
            .build();
    }

}
