package pl.wit.ilog.form.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "uuid")
final class FormResponse {
    private UUID uuid;
    private String formName;
    private Date created;
    private List<QuestionResponse> questions;
    private UserDto user;

    @Getter
    @Setter
    static final class UserDto {
        private Long id;
        private String username;
    }
}
