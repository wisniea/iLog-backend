package pl.wit.ilog.form.question;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.wit.ilog.form.answer.AnswerEntity;
import pl.wit.ilog.form.model.FormEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Transient
    @Column(name = "type", nullable = false)
    private QuestionTypeEnum type;

    @OneToMany(cascade=CascadeType.ALL)
    private List<AnswerEntity> answers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "form_id", nullable = false)
    private FormEntity form;

    public void addAnswer(@NotNull final AnswerEntity answer){
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(@NotNull final AnswerEntity answer){
        answers.remove(answer);
        answer.setQuestion(null);
    }
}