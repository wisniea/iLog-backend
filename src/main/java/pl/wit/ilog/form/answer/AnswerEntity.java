package pl.wit.ilog.form.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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

    @Column(name = "text", nullable = false)
    private String text;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;
}
