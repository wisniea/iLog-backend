package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wit.ilog.common.IMapper;
import pl.wit.ilog.form.answer.IAnswerRepo;
import pl.wit.ilog.form.model.FormEntity;
import pl.wit.ilog.form.model.IModelRepo;
import pl.wit.ilog.form.question.IQuestionRepo;
import pl.wit.ilog.form.question.QuestionEntity;
import pl.wit.ilog.form.question.QuestionRequest;
import pl.wit.ilog.user.model.UserEntity;
import pl.wit.ilog.user.model.UserRepository;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {

    private final IAnswerRepo answerRepo;

    private final IModelRepo modelRepo;

    private final IQuestionRepo questionRepo;

    private final UserRepository userRepository;

    private final IMapper<FormEntity, FormResponse> mapper;

    @PostMapping("/create")
    public ResponseEntity<FormResponse> create(@NotNull final FormCreateRequest request,
                                               @NotNull final UserEntity user) throws Exception {
        val form = new FormEntity();
        form.setUuid(UUID.randomUUID());
        form.setDate(new Date());
        form.setName(request.getFormName());
        //form.setQuestion(new ArrayList<>(form.getQuestion()));

        val questions = request.getQuestions().stream()
                .forEach((q) -> {
                    val answers = q.getAnswers();
                    answers.stream().forEach((a) -> {
                        a.setName(request.getQuestions().stream());
                    });
                });

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
        val response = mapper.map(form);

        return ResponseEntity.created(new URI("/forms/" + response.getUuid().toString()))
                .body(response);
    }

    private QuestionEntity addQuestion(QuestionRequest request){
        //val question = new QuestionEntity();

    }
}
