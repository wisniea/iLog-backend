package pl.wit.ilog.form.model;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wit.ilog.form.answer.AnswerEntity;

public interface IModelRepo extends JpaRepository<AnswerEntity, Long> {

}
