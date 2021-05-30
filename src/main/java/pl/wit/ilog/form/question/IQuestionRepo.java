package pl.wit.ilog.form.question;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wit.ilog.form.answer.AnswerEntity;

public interface IQuestionRepo extends JpaRepository<AnswerEntity, Long> {

}
