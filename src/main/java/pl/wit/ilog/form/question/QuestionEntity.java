package pl.wit.ilog.form.question;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.wit.ilog.form.answer.AnswerEntity;
import pl.wit.ilog.form.model.FormEntity;

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
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "question")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private QuestionTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "form_id", nullable = false, updatable = false)
    private FormEntity form;

    @OneToMany(mappedBy = "question")
    private List<AnswerEntity> answers = new ArrayList<>();
}