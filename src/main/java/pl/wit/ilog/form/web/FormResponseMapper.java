package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import pl.wit.ilog.form.model.FormEntity;
import pl.wit.ilog.form.question.QuestionEntity;
import pl.wit.ilog.internals.web.IMapper;
import pl.wit.ilog.user.model.UserEntity;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class FormResponseMapper implements IMapper<FormEntity, FormResponse> {

    private final IMapper<QuestionEntity, QuestionResponse> questionMapper;

    @Override
    public FormResponse map(FormEntity e) {
        val form = new FormResponse();
        form.setFormName(e.getName());
        form.setCreated(e.getDate());
        form.setUuid(e.getUuid());
        form.setUser(map(e.getCreatedBy()));
        val questions = e.getQuestions().stream()
                .map(questionMapper::map)
                .collect(Collectors.toList());
        form.setQuestions(questions);
        return form;
    }
    private static FormResponse.UserDto map(final UserEntity user){

        if(user == null)
            return null;

        val dto = new FormResponse.UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }
}
