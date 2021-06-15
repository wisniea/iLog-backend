package pl.wit.ilog.vote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AgePickCount {

    private Long answerId;
    private AgeEnum ageEnum;
    private Long count;
}
