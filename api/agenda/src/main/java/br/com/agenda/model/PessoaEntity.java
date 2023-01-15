package br.com.agenda.model;

import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@Table(name = "pessoa")
public class PessoaEntity implements Serializable {

    private static final long serialVersionUID = -7869035609286835054L;
    private static final String SEQUENCE = "pessoa_sequence";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @Column(name = "id", length = 10)
    private Long id;

    @Column(name = "nome", nullable = false, length = 60)
    private String nome;

    @Column(name = "idade", nullable = false)
    private int idade;

    @Column(name = "imagem", length = 1000)
    private String linkImagem;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id_cidade", referencedColumnName = "id", nullable = false)
    private CidadeEntity cidade;

}
