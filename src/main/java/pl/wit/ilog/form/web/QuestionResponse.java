package pl.wit.ilog.form.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.wit.ilog.form.question.QuestionTypeEnum;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
final class QuestionResponse {
    private Long id;
    private QuestionTypeEnum type;
    private List<AnswerResponse> answers;

    @Getter
    @Setter
    @EqualsAndHashCode(of = "id")
    static final class AnswerDto {
        private Long id;
        private String text;
    }
}
