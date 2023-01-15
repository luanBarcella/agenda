package br.com.agenda.mapper.cidade;


import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import br.com.agenda.dto.request.cidade.CriarCidadeRequest;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.service.support.ConfigService;
import br.com.agenda.service.support.ConfigService.Key;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CriarCidadeRequestMapper
    implements Function<CriarCidadeRequest, CidadeEntity> {

    private final ConfigService configService;

    @Override
    public CidadeEntity apply(final CriarCidadeRequest request) {
        return CidadeEntity.builder()
            .nome(request.getNome())
            .UF(request.getUF())
            .linkImagem(ofNullable(
                request.getLinkImagem())
                .orElseGet(() -> configService.get(Key.URL_PADRAO_CIDADES)))
            .locais(new ArrayList<>())
            .build();
    }

}
