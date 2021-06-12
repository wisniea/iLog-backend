package pl.wit.ilog.form.question;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class QuestionRequest {


    @NotEmpty(message = "missing")
    @Size(max = 511, message = "Too big")
    private String question;

    @NotEmpty(message = "missing")
    private QuestionTypeEnum type;

}
