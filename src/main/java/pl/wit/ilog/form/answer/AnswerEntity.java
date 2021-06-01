package pl.wit.ilog.form.answer;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import pl.wit.ilog.form.question.QuestionEntity;

import javax.persistence.*;
import java.util.UUID;

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

    @NaturalId
    @Type(type="uuid-char")
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private Boolean value;

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;
}
