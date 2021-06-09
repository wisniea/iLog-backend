package pl.wit.ilog.form.question;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.wit.ilog.form.answer.AnswerEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "question")
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private QuestionTypeEnum type;

    @OneToMany(
            mappedBy = "id",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<AnswerEntity> answers = new ArrayList<>();
}