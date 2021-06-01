package pl.wit.ilog.form.question;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import pl.wit.ilog.form.answer.AnswerEntity;
import pl.wit.ilog.form.model.FormEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

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

    @NaturalId
    @Type(type="uuid-char")
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private QuestionTypeEnum type;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<AnswerEntity> answers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL, optional = false)
    @JoinColumn(name = "form_id", nullable = false)
    private FormEntity form;

}