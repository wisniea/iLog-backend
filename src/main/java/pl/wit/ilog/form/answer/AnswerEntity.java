package pl.wit.ilog.form.answer;

import lombok.*;
import pl.wit.ilog.form.question.QuestionEntity;

import javax.persistence.*;

@Table(name = "answer")
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
//
//    @Column(name = "question_id", nullable = false)
//    private Long questionId;

    @Column(name = "value", nullable = false)
    private Boolean value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;
}
