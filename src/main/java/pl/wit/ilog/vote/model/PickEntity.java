package pl.wit.ilog.vote.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "pick")
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class PickEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "answer_id", nullable = false)
    private Long answerId;

    // ?
    @Column(name = "text_answer")
    private String textAnswer;
}