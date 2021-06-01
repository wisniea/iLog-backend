package pl.wit.ilog.form.question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionRepo extends JpaRepository<QuestionEntity, Long> {

}
