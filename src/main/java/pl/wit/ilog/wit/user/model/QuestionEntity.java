package pl.wit.ilog.wit.user.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "question_text", nullable = false)
    private String question;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "poll_id", nullable = false)
    private PollEntity poll;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    private List<AnswerEntity> answers = new ArrayList<>();
}
