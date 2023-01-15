package br.com.agenda.service.pessoa;

import org.springframework.stereotype.Service;

import br.com.agenda.mapper.pessoa.CriarPessoaRequestMapper;
import br.com.agenda.dto.request.pessoa.CriarPessoaRequest;
import br.com.agenda.dto.response.pessoa.CriarPessoaResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.PessoaEntity;
import br.com.agenda.repository.PessoaRepository;
import br.com.agenda.service.support.PegarCidadeService;
import br.com.agenda.validator.pessoa.CriarPessoaRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriarPessoaService {

    private final PegarCidadeService pegarCidadeService;
    private final PessoaRepository pessoaRepository;
    private final CriarPessoaRequestMapper criarPessoaRequestMapper;
    private final CriarPessoaRequestValidator criarPessoaRequestValidator;

    public CriarPessoaResponse criar(final CriarPessoaRequest request){
        log.info("Iniciando processo de criação de pessoa para: {}", request);
        criarPessoaRequestValidator.accept(request);

        final CidadeEntity cidade = pegarCidadeService.porId(request.getIdCidade());

        final PessoaEntity pessoa = criarPessoaRequestMapper.apply(request, cidade);

        pessoaRepository.save(pessoa);

        return CriarPessoaResponse.builder()
            .idPessoa(pessoa.getId())
            .build();
    }

}
