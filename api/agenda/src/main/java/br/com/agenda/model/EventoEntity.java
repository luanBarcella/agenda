package br.com.agenda.model;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.agenda.enums.Categoria;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@Table(name = "evento")
public class EventoEntity implements Serializable {

    private static final long serialVersionUID = 6515373678387328730L;
    private static final String SEQUENCE = "evento_sequence";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @Column(name = "id", length = 10)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "data_inicio", nullable = false)
    private OffsetDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private OffsetDateTime dataFim;

    @Column(name = "imagem", length = 1000)
    private String linkImagem;

    @Column(name = "categoria", nullable = false, length = 100)
    @Enumerated(value = STRING)
    private Categoria categoria;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id_local", referencedColumnName = "id", nullable = false)
    private LocalEntity localEvento;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
        name = "pessoa_evento",
        joinColumns = @JoinColumn(name = "id_evento", referencedColumnName = "id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "id_pessoa", referencedColumnName = "id", nullable = false)
    )
    private List<PessoaEntity> pessoas;
}
