package pl.wit.ilog.wit.form.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wit.ilog.wit.form.answer.IAnswerRepo;
import pl.wit.ilog.wit.form.model.IModelRepo;
import pl.wit.ilog.wit.form.question.IQuestionRepo;
import pl.wit.ilog.wit.user.model.UserRepository;

@RestController
@RequestMapping("/form")
@RequiredArgsConstructor
public class FormController {

    private final IAnswerRepo answerRepo;

    private final IModelRepo modelRepo;

    private final IQuestionRepo questionRepo;

    private final UserRepository userRepository;
}
