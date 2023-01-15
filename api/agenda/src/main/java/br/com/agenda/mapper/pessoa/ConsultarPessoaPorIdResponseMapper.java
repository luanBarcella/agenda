package br.com.agenda.mapper.pessoa;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.agenda.dto.response.pessoa.ConsultarPessoaPorIdCidadeResponse;
import br.com.agenda.dto.response.pessoa.ConsultarPessoaPorIdLocalResponse;
import br.com.agenda.dto.response.pessoa.ConsultarPessoaPorIdResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.LocalEntity;
import br.com.agenda.model.PessoaEntity;

public class ConsultarPessoaPorIdResponseMapper implements Function<PessoaEntity, ConsultarPessoaPorIdResponse> {

    @Override
    public ConsultarPessoaPorIdResponse apply(final PessoaEntity pessoa) {
        return ConsultarPessoaPorIdResponse.builder()
            .id(pessoa.getId())
            .nome(pessoa.getNome())
            .idade(pessoa.getIdade())
            .linkImagem(pessoa.getLinkImagem())
            .email(pessoa.getEmail())
            .cidade(buildCidadeResponse(pessoa.getCidade()))
            .build();
    }

    private ConsultarPessoaPorIdCidadeResponse buildCidadeResponse(final CidadeEntity cidade){
        return ConsultarPessoaPorIdCidadeResponse.builder()
            .id(cidade.getId())
            .nome(cidade.getNome())
            .UF(cidade.getUF())
            .linkImagem(cidade.getLinkImagem())
            .locais(buildLocaisResponse(cidade.getLocais()))
            .build();
    }

    private List<ConsultarPessoaPorIdLocalResponse> buildLocaisResponse(final List<LocalEntity> locais){
        return locais.stream()
            .map(local -> ConsultarPessoaPorIdLocalResponse.builder()
                .id(local.getId())
                .nome(local.getNome())
                .linkImagem(local.getLinkImagem())
                .build())
            .collect(Collectors.toList());
    }
}
