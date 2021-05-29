package pl.wit.ilog.wit.form.question;

import lombok.*;
import pl.wit.ilog.wit.form.answer.AnswerEntity;

import javax.persistence.*;

@Table(name = "question")
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Transient
    @Column(name = "type", nullable = false)
    private QuestionTypeEnum type;

    @ManyToOne
    private AnswerEntity answer;
}