package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wit.ilog.form.answer.AnswerEntity;
import pl.wit.ilog.form.answer.IAnswerRepo;
import pl.wit.ilog.form.model.FormEntity;
import pl.wit.ilog.form.model.IModelRepo;
import pl.wit.ilog.form.question.IQuestionRepo;
import pl.wit.ilog.form.question.QuestionEntity;
import pl.wit.ilog.form.question.QuestionRequest;
import pl.wit.ilog.util.IMapper;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {

    private final IAnswerRepo answerRepo;

    private final IModelRepo modelRepo;

    private final IQuestionRepo questionRepo;

    //private final IUserRepository userRepository;

    private final IMapper<FormEntity, FormResponse> mapper;

    //@PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<FormResponse> create(@RequestBody @NotNull final FormCreateRequest request/*,
                                               @NotNull final UserEntity user*/) throws Exception {
        val form = new FormEntity();
        //form.setId(1l); ///////temp
        form.setUuid(UUID.randomUUID());
        form.setDate(new Date());
        form.setName(request.getFormName());
        mapQuestion(request, form);
        modelRepo.save(form);
        val response = mapper.map(form);

        return ResponseEntity.created(new URI("/forms/" + response.getUuid().toString()))
                .body(response);
    }

    // wyciągnij z FormCreateRequest QuestionRequest i zrób z każdego z nich QuestionEntity -> użyj addQuestion z pollEntity
    private void mapQuestion(@RequestBody @NotNull FormCreateRequest request, FormEntity form){
        val questionRequests = request.getQuestions();
        questionRequests.stream().forEach((qr) -> {
            QuestionEntity question = new QuestionEntity();
            question.setName(qr.getName());
            question.setType(qr.getType());
            mapAnswer(qr, question);
            question.setForm(form);
            form.addQuestion(question);
        });
    }

    // wyciągnij z QuestionRequest AnswerRequest i zrób z każdego z nich AnswerEntity
    private void mapAnswer(@RequestBody @NotNull QuestionRequest request, QuestionEntity question){
        val answerRequests = request.getAnswers();
        answerRequests.stream().forEach((a) -> {
            AnswerEntity answer = new AnswerEntity();
            answer.setName(a.getName());
            answer.setValue(a.getValue());
            answer.setQuestion(question);
            //answer.setQuestionId(question.getId()); // chyba do wywalenia
            question.addAnswer(answer);
        });
    }
}
