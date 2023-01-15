package br.com.agenda.enums;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum Categoria {

    ESPORTIVO(1, "Esportivo"),
    TRABALHO(2, "Trabalho"),
    LAZER(3, "Lazer"),
    SOCIAL(4, "Social"),
    ESTUDO(5, "Estudo");

    private int id;
    private String nome;

}
