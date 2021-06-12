package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class AnswerController {
    private final IAnswerRepo answerRepo;

    private final IQuestionRepo questionRepo;

    private final IMapper<AnswerEntity, AnswerResponse> mapper;



    @PreAuthorize("hasRole('USER')")
    @PostMapping("{formUuid}/questions/{questionId}")
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
}
