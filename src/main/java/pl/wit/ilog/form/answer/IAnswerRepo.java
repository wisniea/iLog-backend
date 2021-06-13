package pl.wit.ilog.form.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wit.ilog.form.question.QuestionEntity;

import java.util.stream.Stream;

public interface IAnswerRepo extends JpaRepository<AnswerEntity, Long> {
    Stream<AnswerEntity> findAllByQuestion(QuestionEntity question);
}
