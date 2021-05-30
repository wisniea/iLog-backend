package pl.wit.ilog.form.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import pl.wit.ilog.form.question.QuestionEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "form")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class FormEntity {
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

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date date;

    @OneToMany(
            mappedBy = "form",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Size(min = 2, max = 6)
    @Fetch(FetchMode.SELECT)
    private List<QuestionEntity> questions = new ArrayList<>();

    public void addQuestion(@NotNull final QuestionEntity question){
        questions.add(question);
        question.setForm(this);
    }

    public void removeQuestion(@NotNull final QuestionEntity question){
        questions.remove(question);
        question.setForm(null);
    }
}
