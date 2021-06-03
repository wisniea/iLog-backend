package pl.wit.ilog.form.vote;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.wit.ilog.form.answer.AnswerEntity;

import javax.persistence.*;

@Table(name = "vote")
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "answer_id", nullable = false)
    private AnswerEntity answer;
}
