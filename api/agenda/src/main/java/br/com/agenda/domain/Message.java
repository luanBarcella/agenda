package br.com.agenda.domain;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum Message {

    CAMPOS_ALTERADOS_SUCESSO("campos.alterados.sucesso"),
    CIDADE_ALTERADA_SUCESSO("Cidade alterada com sucesso."),
    CIDADE_APAGADA_SUCESSO("cidade.apagada.sucesso"),
    CIDADE_ID_INVALIDO("cidade.id-invalido"),
    CIDADE_JA_REGISTRADA("cidade.ja.registrada"),
    CIDADE_NAO_INFORMADA("cidade.nao-informada"),

    EVENTO_JA_REGISTRADO("evento.ja.registrado"),

    INTERVALO_ENTRE_DATAS_INVALIDO("intervalo-datas.invalido"),

    LINK_IMAGEM_ALTERADO_SUCESSO("link-imagem.alterado.sucesso"),
    LOCAL_APAGADO_SUCESSO("local.apagado.sucesso"),
    LOCAL_ID_INVALIDO("local.id-invalido"),
    LOCAL_JA_REGISTRADO("local.ja.registrado"),
    LOCAL_NAO_INFORMADO("local.nao-informado"),

    NENHUM_CAMPO_ALTERADO("nenhum.campo.alterado"),
    NOME_ALTERADO_SUCESSO("nome.alterado.sucesso"),

    PESSOA_ID_INVALIDO("pessoa.id-invalido"),
    PESSOA_JA_REGISTRADA("pessoa.ja.registrada"),

    REQUEST_INVALIDA("request.invalida"),
    REQUEST_NAO_INFORMADA("requisicao.nao-informada"),

    UF_ALTERADO_SUCESSO("uf.alterado.sucesso");

    private String mensagem;
}
