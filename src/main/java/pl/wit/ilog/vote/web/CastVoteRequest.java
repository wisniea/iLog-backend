package pl.wit.ilog.vote.web;

import lombok.Getter;
import lombok.Setter;
import pl.wit.ilog.vote.model.AgeEnum;
import pl.wit.ilog.vote.model.GenderEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CastVoteRequest {

    @NotEmpty(message = "missing")
    private GenderEnum gender;

    @NotNull
    private AgeEnum age;

    @NotNull
    private List<CastPickRequest> answers = new ArrayList<>();

}
