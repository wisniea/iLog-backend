package pl.wit.ilog.vote.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import pl.wit.ilog.form.model.IFormRepo;
import pl.wit.ilog.internals.exception.EntityNotFoundException;
import pl.wit.ilog.vote.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class VoteController {

    private final IVoteRepo voteRepo;

    private final IPickRepo pickRepo;

    private final IFormRepo formRepo;

    @PostMapping("{uuid}/castVote")
    public VoteEntity castVote(@RequestBody @NotNull final CastVoteRequest request,
                               @PathVariable @NotNull final UUID uuid,
                               @NotNull HttpServletRequest httpRequest){
        val vote = new VoteEntity();
        vote.setAge(request.getAge());
        vote.setSex(request.getSex());
        vote.setIp(httpRequest.getRemoteAddr());
        vote.setPicks(request.getAnswers().stream().map(answer -> {
            val pick = new PickEntity();
            pick.setAnswerId(answer.getAnswerId());
            pick.setTextAnswer(answer.getTextAnswer());
            pickRepo.save(pick);
            return pick;
        }).collect(Collectors.toList()));

        val form = formRepo.findByUuid(uuid)
                .orElseThrow(EntityNotFoundException::new);
        vote.setForm(form);

        voteRepo.save(vote);
        return vote;
    }

    @GetMapping("{uuid}/insights/{answerId}")
    List<AnswerPickCount> getAllByAnswerId(@PathVariable(name = "answerId") @NotNull Long answerId){
        return voteRepo.allAnswerIdPicksAmount(answerId);
    }

    @GetMapping("{uuid}/insights/age/{answerId}")
    List<AnswerPickCount> getAllByAge(@PathVariable(name = "answerId") @NotNull Long answerId){
        return voteRepo.findAllByAge();
    }
}
