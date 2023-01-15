package br.com.agenda.service.local;

import org.springframework.stereotype.Service;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.repository.LocalRepository;
import br.com.agenda.validator.local.CriarLocalRequestValidator;
import br.com.agenda.dto.request.local.CriarLocalRequest;
import br.com.agenda.dto.response.local.CriarLocalResponse;
import br.com.agenda.mapper.local.CriarLocalRequestMapper;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.service.support.PegarCidadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriarLocalService {

    private final PegarCidadeService pegarCidadeService;
    private final LocalRepository localRepository;
    private final CriarLocalRequestMapper criarLocalRequestMapper;
    private final CriarLocalRequestValidator criarLocalRequestValidator;

    public CriarLocalResponse criar(final CriarLocalRequest request){
        log.info("Iniciando o processo de criacao de cidade para: {}", request);
        criarLocalRequestValidator.accept(request);

        final CidadeEntity cidadeOrigemLocal = pegarCidadeService.porId(request.getIdCidade());

        final LocalEntity local = criarLocalRequestMapper.apply(request, cidadeOrigemLocal);

        localRepository.save(local);
        cidadeOrigemLocal.getLocais().add(local);

        return CriarLocalResponse.builder()
            .id(local.getId())
            .build();
    }

}
