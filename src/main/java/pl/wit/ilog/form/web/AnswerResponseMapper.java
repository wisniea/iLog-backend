package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import pl.wit.ilog.form.answer.AnswerEntity;
import pl.wit.ilog.internals.web.IMapper;

@Service
@RequiredArgsConstructor
class AnswerResponseMapper implements IMapper<AnswerEntity, AnswerResponse> {

    @Override
    public AnswerResponse map(AnswerEntity a) {
        val answer = new AnswerResponse();
        answer.setId(a.getId());
        answer.setText(a.getText());

        return answer;
    }
}
