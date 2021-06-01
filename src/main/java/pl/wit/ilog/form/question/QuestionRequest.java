package pl.wit.ilog.form.question;

import lombok.Getter;
import lombok.Setter;
import pl.wit.ilog.form.answer.AnswerRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionRequest {


    @NotEmpty(message = "missing")
    @Size(max = 511, message = "Too big")
    private String name;

    @NotEmpty(message = "missing")
    private QuestionTypeEnum type;

    @NotNull
    private List<AnswerRequest> answers = new ArrayList<>();

}
