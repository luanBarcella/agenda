package br.com.agenda.service.cidade;

import org.springframework.stereotype.Service;

import br.com.agenda.dto.request.cidade.CriarCidadeRequest;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.validator.cidade.CriarCidadeRequestValidator;
import br.com.agenda.dto.response.cidade.CriarCidadeResponse;
import br.com.agenda.mapper.cidade.CriarCidadeRequestMapper;
import br.com.agenda.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CriarCidadeService {

    private final CidadeRepository cidadeRepository;
    private final CriarCidadeRequestValidator criarCidadeRequestValidator;
    private final CriarCidadeRequestMapper criarCidadeRequestMapper;

    public CriarCidadeResponse criar(final CriarCidadeRequest request){
        log.info("Iniciando processo de criação de cidade para: {}", request);
        criarCidadeRequestValidator.accept(request);

        final CidadeEntity novaCidade = criarCidadeRequestMapper.apply(request);

        cidadeRepository.save(novaCidade);

        return CriarCidadeResponse.builder()
            .id(novaCidade.getId())
            .build();
    }

}
