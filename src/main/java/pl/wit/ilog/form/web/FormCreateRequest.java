package pl.wit.ilog.form.web;

import lombok.Getter;
import lombok.Setter;
import pl.wit.ilog.form.question.QuestionRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
final class FormCreateRequest {

    @NotEmpty(message = "missing")
    @Size(max = 255, message = "too-big")
    private String formName;

    @NotNull
    private List<QuestionRequest> questions;

}
