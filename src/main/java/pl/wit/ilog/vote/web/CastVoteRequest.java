package pl.wit.ilog.vote.web;

import lombok.Getter;
import lombok.Setter;
import pl.wit.ilog.vote.model.SexEnum;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CastVoteRequest {

    @NotNull
    private UUID formId;

    @NotEmpty(message = "missing")
    private SexEnum sex;

    @Min(0)
    @Max(100)
    @NotNull
    private Integer age;

    @NotNull
    private List<CastAnswerRequest> answers = new ArrayList<>();

}
