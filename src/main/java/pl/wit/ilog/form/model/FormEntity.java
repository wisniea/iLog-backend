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
import java.util.*;

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

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private Set<QuestionEntity> questions = new HashSet<>();
}
