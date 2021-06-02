package pl.wit.ilog.form.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import pl.wit.ilog.form.question.QuestionEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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

    @JsonIgnore
    @Column(updatable = false)
    private Long createdBy;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date date;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private Set<QuestionEntity> questions = new HashSet<>();
}
