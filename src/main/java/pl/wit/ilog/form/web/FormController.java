package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wit.ilog.user.model.IUserRepository;
import pl.wit.ilog.util.IMapper;
import pl.wit.ilog.form.answer.AnswerEntity;
import pl.wit.ilog.form.answer.IAnswerRepo;
import pl.wit.ilog.form.model.FormEntity;
import pl.wit.ilog.form.model.IModelRepo;
import pl.wit.ilog.form.question.IQuestionRepo;
import pl.wit.ilog.form.question.QuestionEntity;
import pl.wit.ilog.form.question.QuestionRequest;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {

    private final IAnswerRepo answerRepo;

    private final IModelRepo modelRepo;

    private final IQuestionRepo questionRepo;

    private final IUserRepository userRepository;

    private final IMapper<FormEntity, FormResponse> mapper;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<FormResponse> create(@NotNull final FormCreateRequest request/*,
                                               @NotNull final UserEntity user*/) throws Exception {
        val form = new FormEntity();
        form.setUuid(UUID.randomUUID());
        form.setDate(new Date());
        form.setName(request.getFormName());
        //form.setQuestion(new ArrayList<>(form.getQuestion()));
        val questionsList = form.getQuestions();
        form.setQuestions(questionsList);
        /*val questions = request.getQuestions().stream()
                .map((q) -> {
                    val question = new QuestionEntity();
                    question.setName(q.getName());
                    question.setType(q.getType());
                    question.setAnswer(q.getAnswers().stream()
                            .map((a) -> {
                                val answer = new AnswerEntity();
                                answer.setName(a.getName());
                                answer.setValue(a.getValue());
                                return answer;
                            }).collect(Collectors.toCollection(new ArrayList<AnswerEntity>())));
                })        */
        modelRepo.save(form);
        val response = mapper.map(form);

        return ResponseEntity.created(new URI("/forms/" + response.getUuid().toString()))
                .body(response);
    }

    private QuestionEntity addQuestion(QuestionRequest request){
        val question = new QuestionEntity();
        question.setName(request.getName());
        question.setType(request.getType());
        question.setAnswers(request.getAnswers().stream()
                                .map(answerRequest -> {
                                    val answer = new AnswerEntity();
                                    answer.setName(answerRequest.getName());
                                    answer.setValue(answerRequest.getValue());
                                    return answer;
                                }).collect(Collectors.toList()));
        return question;
    }
}
