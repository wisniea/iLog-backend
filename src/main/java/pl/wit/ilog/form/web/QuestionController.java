package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.wit.ilog.form.model.IFormRepo;
import pl.wit.ilog.form.question.IQuestionRepo;
import pl.wit.ilog.form.question.QuestionEntity;
import pl.wit.ilog.form.question.QuestionRequest;
import pl.wit.ilog.internals.exception.EntityNotFoundException;
import pl.wit.ilog.internals.web.IMapper;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class QuestionController {

    private final IFormRepo formRepo;

    private final IQuestionRepo questionRepo;

    private final IMapper<QuestionEntity, QuestionResponse> mapper;

    @Transactional
    @GetMapping("{formUuid}/questions/{id}")
    public QuestionResponse getForm(@NotNull @PathVariable final Long id,
                                    @Valid @PathVariable("formUuid") final UUID formUuid) {
        return questionRepo.findById(id)
                .map(mapper::map)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @GetMapping("/{formUuid}/questions")
    public List<QuestionResponse> getAllAnswers(@Valid @PathVariable("formUuid") final UUID formUuid) {
        val form = formRepo.findByUuid(formUuid)
                .orElseThrow(EntityNotFoundException::new);

        return questionRepo.findAllByForm(form)
                .map(mapper::map)
                .collect(Collectors.toList());
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("{formUuid}/questions")
    public ResponseEntity<QuestionResponse> create(@RequestBody @NotNull final QuestionRequest request,
                                 @Valid @PathVariable("formUuid") final UUID formUuid) throws Exception{
        val question = new QuestionEntity();

        val form = formRepo.findByUuid(formUuid)
                .orElseThrow(EntityNotFoundException::new);

            question.setQuestion(request.getQuestion());
            question.setType(request.getType());
            question.setForm(form);
            questionRepo.save(question);
        val response = mapper.map(question);
        return ResponseEntity.created(new URI("/forms/" + formUuid + "/questions/" + response.getId().toString()))
                .body(response);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("{formUuid}/questions/{id}")
    void delete(@NotNull @PathVariable final Long id,@Valid @PathVariable("formUuid") final UUID formUuid) {
        val question = questionRepo.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        questionRepo.delete(question);
    }
}
