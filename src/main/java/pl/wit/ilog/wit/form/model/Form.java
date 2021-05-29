package pl.wit.ilog.wit.form.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import pl.wit.ilog.wit.form.question.QuestionEntity;
import pl.wit.ilog.wit.user.model.UserEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "form")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Form {
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

    @OneToMany
    @Column(name = "user", nullable = false)
    private Set<UserEntity> user;

    @ManyToOne
    private QuestionEntity question;
}
