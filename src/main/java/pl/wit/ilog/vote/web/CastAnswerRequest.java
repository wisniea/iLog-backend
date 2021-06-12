package pl.wit.ilog.vote.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CastAnswerRequest {

    @NotNull
    private Long answerId;
}
