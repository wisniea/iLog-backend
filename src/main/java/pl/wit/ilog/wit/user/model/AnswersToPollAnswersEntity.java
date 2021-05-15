package pl.wit.ilog.wit.user.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = {"fillPollEntity", "answerEntity"})
@Entity
@Table(name = "answers_to_poll_answers")
public class AnswersToPollAnswersEntity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "fill_poll_id")
    private FillPollEntity fillPollEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private AnswerEntity answerEntity;

    @Column(name = "answer_checked", nullable = false)
    private boolean answerChecked;
}
