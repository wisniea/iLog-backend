package pl.wit.ilog.wit.form.question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionRepo extends JpaRepository<Long, QuestionEntity> {

}
