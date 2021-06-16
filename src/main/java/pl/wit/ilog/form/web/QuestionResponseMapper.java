package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import pl.wit.ilog.form.answer.AnswerEntity;
import pl.wit.ilog.form.question.QuestionEntity;
import pl.wit.ilog.internals.web.IMapper;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class QuestionResponseMapper implements IMapper<QuestionEntity, QuestionResponse> {
    private final IMapper<AnswerEntity, AnswerResponse> answerMapper;
    @Override
    public QuestionResponse map(QuestionEntity q) {
        val question = new QuestionResponse();
        question.setId(q.getId());
        question.setQuestion(q.getQuestion());
        question.setType(q.getType());
        question.setAnswers(q.getAnswers()
                .stream()
                .map(answerMapper::map)
                .collect(Collectors.toList()));
        return question;
    }
}
