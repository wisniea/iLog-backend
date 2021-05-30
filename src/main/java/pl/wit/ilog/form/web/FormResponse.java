package pl.wit.ilog.form.web;

import lombok.Getter;
import lombok.Setter;
import pl.wit.ilog.form.question.QuestionTypeEnum;

import java.util.UUID;

@Getter
@Setter
public class FormResponse {

    private UUID uuid;
    private String name;
    private UserDto user;
    private QuestionDto questions;

    @Getter
    @Setter
    static final class QuestionDto {

        private Long id;
        private String name;
        private QuestionTypeEnum type;
        private AnswerDto answers;

        @Getter
        @Setter
        static final class AnswerDto {

            private Long id;
            private Long questionId;
            private String name;
            private Boolean value;
        }
    }

    @Getter
    @Setter
    static final class UserDto {

        private Long id;
        private String username;
    }


}
