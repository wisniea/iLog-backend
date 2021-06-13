package pl.wit.ilog.vote.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CastPickRequest {

    @NotNull
    private Long answerId;

    @Size(max = 1024)
    private String textAnswer;
}

