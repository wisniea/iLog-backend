package pl.wit.ilog.wit.user.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "fill_polls")
public class FillPollEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "date", nullable = false)
    private Instant date;

    @OneToMany(mappedBy = "fillPollEntity")
    private List<AnswersToPollAnswersEntity> answersToPollAnswersEntities = new ArrayList<>();

}
