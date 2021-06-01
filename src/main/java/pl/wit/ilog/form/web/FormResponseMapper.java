package pl.wit.ilog.form.web;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import pl.wit.ilog.user.model.UserEntity;
import pl.wit.ilog.util.IMapper;
import pl.wit.ilog.form.answer.AnswerEntity;
import pl.wit.ilog.form.model.FormEntity;
import pl.wit.ilog.form.question.QuestionEntity;

@RequiredArgsConstructor
@Service
public class FormResponseMapper implements IMapper<Object, FormResponse> {

    @Override
    public FormResponse map(final @NotNull Object o) {

        if(!(o instanceof FormEntity)){
            throw new IllegalArgumentException(o.getClass().getName() + " cannot be casted to FormEntity");
        }
        val form = (FormEntity) o;

        val formResponse = new FormResponse();
        formResponse.setUuid(form.getUuid());
        formResponse.setName(form.getName());
        //formResponse.setUser(map(o).getUser());
        //formResponse.setQuestions(map(form.getQuestions()));
        
        return formResponse;
    }

    private FormResponse.QuestionDto map(final QuestionEntity question){
        if(question == null) return null;

        val dto = new FormResponse.QuestionDto();
        dto.setId(question.getId());
        dto.setName(question.getName());
        dto.setType(question.getType());
        dto.setAnswers(map((AnswerEntity) question.getAnswers()));
        return dto;
    }

    private FormResponse.UserDto map(final UserEntity user){
        if(user == null) return null;

        val dto = new FormResponse.UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }

    private FormResponse.QuestionDto.AnswerDto map(final AnswerEntity answer){
        if(answer == null) return null;

        val dto = new FormResponse.QuestionDto.AnswerDto();
        dto.setId(answer.getId());
        dto.setName(answer.getName());
        //dto.setQuestionId(answer.getQuestionId());
        dto.setValue(answer.getValue());
        return dto;
    }
}
