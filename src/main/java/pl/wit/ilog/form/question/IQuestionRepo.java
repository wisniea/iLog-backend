package pl.wit.ilog.form.question;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wit.ilog.form.model.FormEntity;

import java.util.stream.Stream;

public interface IQuestionRepo extends JpaRepository<QuestionEntity, Long> {
    Stream<QuestionEntity> findAllByForm(FormEntity form);
}
