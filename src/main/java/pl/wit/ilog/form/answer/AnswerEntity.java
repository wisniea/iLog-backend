package pl.wit.ilog.form.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import pl.wit.ilog.form.question.QuestionEntity;
import pl.wit.ilog.form.vote.VoteEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
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

//    @Column(name = "value", nullable = false)
//    private Boolean value;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private Set<VoteEntity> votes = new HashSet<>();
}
