package pl.wit.ilog.internals.exception;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import pl.wit.ilog.internals.web.ErrorDto;

@Getter
public abstract class AbstractRestException extends RuntimeException {

    protected final ErrorDto validationDto;

    AbstractRestException(final @NonNull String key, final @NonNull String value) {
        this(new ErrorDto(key, value));
    }

    AbstractRestException(final @NonNull ErrorDto validation) {
        super(validation.toString());
        this.validationDto = validation;
    }

    public abstract HttpStatus getStatus();
}