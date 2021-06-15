package pl.wit.ilog.vote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnswerGenderCount {

    private Long answerId;
    private GenderEnum sexEnum;
    private Long count;
}
