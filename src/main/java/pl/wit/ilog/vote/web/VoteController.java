package pl.wit.ilog.vote.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wit.ilog.form.model.IFormRepo;
import pl.wit.ilog.internals.exception.EntityNotFoundException;
import pl.wit.ilog.vote.model.IPickRepo;
import pl.wit.ilog.vote.model.IVoteRepo;
import pl.wit.ilog.vote.model.PickEntity;
import pl.wit.ilog.vote.model.VoteEntity;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class VoteController {

    private final IVoteRepo voteRepo;

    private final IPickRepo pickRepo;

    private final IFormRepo formRepo;

    @PostMapping("/cast")
    public VoteEntity castVote(@RequestBody @NotNull final CastVoteRequest request,
                               @NotNull HttpServletRequest httpRequest){
        val vote = new VoteEntity();
        vote.setAge(request.getAge());
        vote.setSex(request.getSex());
        vote.setIp(httpRequest.getRemoteAddr());
        vote.setPicks(request.getAnswers().stream().map(answer -> {
            val pick = new PickEntity();
            pick.setAnswerId(answer.getAnswerId());
            pickRepo.save(pick);
            return pick;
        }).collect(Collectors.toList()));

        val form = formRepo.findByUuid(request.getFormId())
                .orElseThrow(EntityNotFoundException::new);
        vote.setForm(form);

        voteRepo.save(vote);
        return vote;
    }
}
