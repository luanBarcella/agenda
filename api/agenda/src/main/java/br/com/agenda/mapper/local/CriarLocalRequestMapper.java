package br.com.agenda.mapper.local;

import static java.util.Optional.ofNullable;

import java.util.function.BiFunction;

import org.springframework.stereotype.Component;

import br.com.agenda.model.CidadeEntity;
import br.com.agenda.service.support.ConfigService;
import br.com.agenda.service.support.ConfigService.Key;
import br.com.agenda.dto.request.local.CriarLocalRequest;
import br.com.agenda.model.LocalEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CriarLocalRequestMapper
    implements BiFunction<CriarLocalRequest, CidadeEntity, LocalEntity> {

    private final ConfigService configService;

    @Override
    public LocalEntity apply(final CriarLocalRequest request, final CidadeEntity cidade) {
        return LocalEntity.builder()
            .nome(request.getNome())
            .cidade(cidade)
            .linkImagem(ofNullable(request.getLinkImagem())
                .orElseGet(() -> configService.get(Key.URL_PADRAO_LOCAIS)))
            .build();
    }

}
