package br.com.agenda.service.cidade;

import static br.com.agenda.domain.Message.CAMPOS_ALTERADOS_SUCESSO;
import static br.com.agenda.domain.Message.LINK_IMAGEM_ALTERADO_SUCESSO;
import static br.com.agenda.domain.Message.NENHUM_CAMPO_ALTERADO;
import static br.com.agenda.domain.Message.NOME_ALTERADO_SUCESSO;
import static br.com.agenda.domain.Message.UF_ALTERADO_SUCESSO;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.agenda.dto.request.cidade.EditarCidadeRequest;
import br.com.agenda.dto.response.cidade.EditarCidadeResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.service.support.MessageService;
import br.com.agenda.validator.cidade.EditarCidadeRequestValidator;
import br.com.agenda.repository.CidadeRepository;
import br.com.agenda.service.support.PegarCidadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EditarCidadeService {

    private final CidadeRepository cidadeRepository;
    private final MessageService messageService;
    private final PegarCidadeService pegarCidadeService;
    private final EditarCidadeRequestValidator editarCidadeRequestValidator;

    public EditarCidadeResponse editar(final Long idCidade, final EditarCidadeRequest request){
        log.info("Iniciando processo de edição para cidade com id: {}, e request: {}", idCidade, request);
        List<String> camposAlterados = new ArrayList<>();

        CidadeEntity cidade = pegarCidadeService.porId(idCidade);

        editarCidadeRequestValidator.accept(request,  cidade);

        if(nonNull(request.getNome()) && isNotBlank(request.getNome())){
            cidade.setNome(request.getNome());
            camposAlterados.add(messageService.get(NOME_ALTERADO_SUCESSO));
        }

        if(nonNull(request.getUF()) && isNotBlank(request.getUF())){
            cidade.setUF(request.getUF());
            camposAlterados.add(messageService.get(UF_ALTERADO_SUCESSO));
        }

        if(nonNull(request.getLinkImagem()) && isNotBlank(request.getLinkImagem())){
            cidade.setLinkImagem(request.getLinkImagem());
            camposAlterados.add(messageService.get(LINK_IMAGEM_ALTERADO_SUCESSO));
        }

        cidadeRepository.save(cidade);

        final String mensagem = camposAlterados.size() == 0 ?
            messageService.get(NENHUM_CAMPO_ALTERADO) :
            messageService.get(CAMPOS_ALTERADOS_SUCESSO) + camposAlterados.size();

        return EditarCidadeResponse.builder()
            .mensagem(mensagem)
            .camposAlterados(camposAlterados)
            .build();
    }
}
