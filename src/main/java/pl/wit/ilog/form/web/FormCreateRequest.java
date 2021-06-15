package pl.wit.ilog.form.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
final class FormCreateRequest {

    @NotEmpty(message = "missing")
    @Size(max = 255, message = "too-big")
    private String formName;

}