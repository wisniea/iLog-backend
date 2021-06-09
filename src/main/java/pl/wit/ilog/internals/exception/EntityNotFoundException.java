package pl.wit.ilog.internals.exception;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import pl.wit.ilog.internals.web.ErrorDto;


@ToString
@Getter
public class EntityNotFoundException extends AbstractRestException {

    public EntityNotFoundException(final @NonNull String entity) {
        this(new ErrorDto(entity, "not-found"));
    }

    public EntityNotFoundException() {
        this(new ErrorDto("request", "entity-not-found"));
    }

    public EntityNotFoundException(final @NonNull ErrorDto validation) {
        super(validation);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}