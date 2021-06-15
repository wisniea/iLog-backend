package pl.wit.ilog.vote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnswerAgeGenderCount {

    private Long answerId;
    private GenderEnum genderEnum;
    private AgeEnum ageEnum;
    private Long count;

}
