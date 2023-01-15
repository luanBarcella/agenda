package br.com.agenda.mapper.pessoa;

import java.util.function.Function;

import br.com.agenda.dto.response.pessoa.ConsultarPessoaCidadeResponse;
import br.com.agenda.dto.response.pessoa.ConsultarPessoaResponse;
import br.com.agenda.model.CidadeEntity;
import br.com.agenda.model.PessoaEntity;

public class ConsultarPessoaResponseMapper implements Function<PessoaEntity, ConsultarPessoaResponse> {

    @Override
    public ConsultarPessoaResponse apply(final PessoaEntity pessoa) {
        return ConsultarPessoaResponse.builder()
            .id(pessoa.getId())
            .nome(pessoa.getNome())
            .idade(pessoa.getIdade())
            .linkImagem(pessoa.getLinkImagem())
            .email(pessoa.getEmail())
            .cidade(buildCidadeResponse(pessoa.getCidade()))
            .build();
    }

    private ConsultarPessoaCidadeResponse buildCidadeResponse(final CidadeEntity cidade){
        return ConsultarPessoaCidadeResponse.builder()
            .id(cidade.getId())
            .nome(cidade.getNome())
            .UF(cidade.getUF())
            .linkImagem(cidade.getLinkImagem())
            .build();
    }
}
