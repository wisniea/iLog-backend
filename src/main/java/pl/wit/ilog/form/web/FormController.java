package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
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

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {

    private final IAnswerRepo answerRepo;

    private final IModelRepo modelRepo;

    private final IQuestionRepo questionRepo;

    //private final IUserRepository userRepository;

    //private final IMapper<FormEntity, FormResponse> mapper;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public FormEntity create(@RequestBody @NotNull final FormCreateRequest request/*,
                                               @NotNull final UserEntity user*/) throws Exception {
        val form = new FormEntity();
        //form.setId(1l); ///////temp
        form.setUuid(UUID.randomUUID());
        form.setDate(new Date());
        form.setName(request.getFormName());
        form.setQuestions(mapQuestion(request,form).collect(Collectors.toSet()));
        ;
        //val response = mapper.map(form);

        return modelRepo.save(form) /*ResponseEntity.created(new URI("/forms/" + response.getUuid().toString()))
                .body(response)*/;
    }

    // wyciągnij z FormCreateRequest QuestionRequest i zrób z każdego z nich QuestionEntity -> użyj addQuestion z pollEntity
    private Stream<QuestionEntity> mapQuestion(@RequestBody @NotNull FormCreateRequest request, FormEntity form){
        val questionRequests = request.getQuestions();
        return questionRequests.stream().map(qr -> {
             QuestionEntity question = new QuestionEntity();
             question.setUuid(UUID.randomUUID());
             question.setName(qr.getName());
             question.setType(qr.getType());
             question.setForm(form);
             question.setAnswers(qr.getAnswers().stream().map(answer ->{
                 val answers = new AnswerEntity();
                 answers.setUuid(UUID.randomUUID());
                 answers.setName(answer.getName());
                 answers.setValue(answer.getValue());
                 answers.setQuestion(question);
                 answerRepo.save(answers);
                 return answers;
             }).collect(Collectors.toSet()));
             questionRepo.save(question);
         return question;
        });
    }
}
