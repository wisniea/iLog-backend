package pl.wit.ilog.form.answer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AnswerRequest {

    @NotEmpty(message = "missing")
    @Size(max = 511, message = "Too big")
    private String name;

}
