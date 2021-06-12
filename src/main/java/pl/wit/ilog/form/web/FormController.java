package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.wit.ilog.form.answer.AnswerEntity;
import pl.wit.ilog.form.answer.IAnswerRepo;
import pl.wit.ilog.form.model.FormEntity;
import pl.wit.ilog.form.model.IFormRepo;
import pl.wit.ilog.form.question.IQuestionRepo;
import pl.wit.ilog.form.question.QuestionEntity;
import pl.wit.ilog.internals.exception.EntityNotFoundException;
import pl.wit.ilog.security.CurrentUser;
import pl.wit.ilog.security.jwt.UserPrincipal;
import pl.wit.ilog.user.model.IUserRepository;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {

    private final IAnswerRepo answerRepo;

    private final IFormRepo formRepo;

    private final IQuestionRepo questionRepo;

    private final IUserRepository userRepository;

//do naprawy: org.postgresql.util.PSQLException: ERROR: operator does not exist: character varying = bigint
//  Wskazówka: No operator matches the given name and argument types. You might need to add explicit type casts.
//  Pozycja: 227
    @GetMapping("/{uuid}")
    public FormEntity getForm(@NotNull @PathVariable final UUID uuid) {
        return formRepo.findByUuid(uuid)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public FormEntity create(@RequestBody @NotNull final FormCreateRequest request,
                             @CurrentUser UserPrincipal currentUser) {
        val form = new FormEntity();

        val user = userRepository.findById(currentUser.getId())
                .orElseThrow(EntityNotFoundException::new);

        form.setCreatedBy(user);
        form.setUuid(UUID.randomUUID());
        form.setDate(new Date());
        form.setName(request.getFormName());
        form.setQuestions(request.getQuestions().stream().map(question -> {

            val questions = new QuestionEntity();
            questions.setQuestion(question.getQuestion());
            questions.setType(question.getType());
            List<AnswerEntity> answers = question.getAnswers().stream().map(answerEntity -> {
                val answer = new AnswerEntity();
                answer.setText(answerEntity.getName());
                answer.setQuestion(questions);
                answerRepo.save(answer);
                return answer;
            }).collect(Collectors.toList());
            questions.setAnswers(answers);
            questionRepo.save(questions);
            return questions;
        }).collect(Collectors.toList()));

        formRepo.save(form);

        return form;
    }

 /*   private Stream<QuestionEntity> mapQuestion(@RequestBody @NotNull FormCreateRequest request, FormEntity form){
        val questionRequests = request.getQuestions();

        return questionRequests.stream().map(qr -> {
             val question = new QuestionEntity();

             question.setUuid(UUID.randomUUID());
             question.setName(qr.getName());
             question.setType(qr.getType());
             question.setForm(form);
             question.setAnswers(qr.getAnswers().stream().map(answer ->{
                 val answers = new AnswerEntity();

                 answers.setUuid(UUID.randomUUID());
                 answers.setName(answer.getName());
                 answers.setQuestion(question);
                 answerRepo.save(answers);

                 return answers;
             }).collect(Collectors.toSet()));

             questionRepo.save(question);

         return question;
        });
    }*/
}
