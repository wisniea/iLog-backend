package pl.wit.ilog.vote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnswerSexCount {

    private Long answerId;
    private SexEnum sexEnum;
    private Long count;
}
