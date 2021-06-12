package pl.wit.ilog.form.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class AnswerResponse {
        private Long id;
        private String text;
}
