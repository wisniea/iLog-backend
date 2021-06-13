package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.wit.ilog.form.answer.AnswerEntity;
import pl.wit.ilog.form.answer.AnswerRequest;
import pl.wit.ilog.form.answer.IAnswerRepo;
import pl.wit.ilog.form.question.IQuestionRepo;
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
public class AnswerController {
    private final IAnswerRepo answerRepo;

    private final IQuestionRepo questionRepo;

    private final IMapper<AnswerEntity, AnswerResponse> mapper;

    @Transactional
    @GetMapping("/{formUuid}/questions/{questionId}/answers/{answerId}")
    public AnswerResponse getAnswer(@Valid @PathVariable("formUuid") final UUID formUuid,
                                  @Valid @PathVariable("questionId") final Long questionId,
                                  @Valid @PathVariable("answerId") final Long answerId) {
        return answerRepo.findById(answerId)
                .map(mapper::map)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @GetMapping("/{formUuid}/questions/{questionId}/answers")
    public List<AnswerResponse> getAllAnswers(@Valid @PathVariable("formUuid") final UUID formUuid,
                                              @Valid @PathVariable("questionId") final Long questionId) {
        val question = questionRepo.findById(questionId)
                .orElseThrow(EntityNotFoundException::new);
        return answerRepo.findAllByQuestion(question)
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{formUuid}/questions/{questionId}")
    public ResponseEntity<AnswerResponse> create(@RequestBody @NotNull final AnswerRequest request,
                                 @Valid @PathVariable("formUuid") final UUID formUuid,
                                 @Valid @PathVariable("questionId") final Long questionId) throws Exception{
        val answer = new AnswerEntity();

        val question = questionRepo.findById(questionId)
                .orElseThrow(EntityNotFoundException::new);

        answer.setText(request.getText());
        answer.setQuestion(question);
        answerRepo.save(answer);
        val response = mapper.map(answer);
        return ResponseEntity.created(new URI("/forms/" + formUuid + "/questions/"+ questionId+ "answers/" + response.getId().toString()))
                .body(response);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{formUuid}/questions/{questionId}/answers/{answerId}")
    public void delete(@Valid @PathVariable("formUuid") final UUID formUuid,
                       @Valid @PathVariable("questionId") final Long questionId,
                       @Valid @PathVariable("questionId") final Long answerId){

        val answer = answerRepo.findById(answerId)
                .orElseThrow(EntityNotFoundException::new);

        answerRepo.delete(answer);
    }
}
