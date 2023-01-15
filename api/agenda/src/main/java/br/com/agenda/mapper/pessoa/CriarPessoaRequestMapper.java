package br.com.agenda.mapper.pessoa;

import static br.com.agenda.service.support.ConfigService.Key.URL_PADRAO_PESSOAS;
import static java.util.Optional.ofNullable;

import java.util.function.BiFunction;

import org.springframework.stereotype.Component;

import br.com.agenda.dto.request.pessoa.CriarPessoaRequest;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.PessoaEntity;
import br.com.agenda.service.support.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CriarPessoaRequestMapper implements BiFunction<CriarPessoaRequest, CidadeEntity, PessoaEntity> {

    private final ConfigService configService;

    @Override
    public PessoaEntity apply(final CriarPessoaRequest request, final CidadeEntity cidade) {
        return PessoaEntity.builder()
            .nome(request.getNome())
            .idade(request.getIdade())
            .linkImagem(ofNullable(request.getLinkImagem())
                .orElseGet(() -> configService.get(URL_PADRAO_PESSOAS)))
            .email(request.getEmail())
            .cidade(cidade)
            .build();
    }
}
