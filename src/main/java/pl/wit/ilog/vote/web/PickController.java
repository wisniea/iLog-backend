package pl.wit.ilog.vote.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import pl.wit.ilog.internals.exception.EntityNotFoundException;
import pl.wit.ilog.vote.model.IPickRepo;
import pl.wit.ilog.vote.model.IVoteRepo;
import pl.wit.ilog.vote.model.PickEntity;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class PickController {

    private final IVoteRepo voteRepo;

    private final IPickRepo pickRepo;

    @PostMapping("{formUuid}/castVote/{voteId}")
    public PickEntity castVote(@RequestBody @NotNull final CastPickRequest request,
                               @PathVariable(name = "formUuid") @NotNull final UUID uuid,
                               @PathVariable(name = "voteId") @NotNull final Long id,
                               @NotNull HttpServletRequest httpRequest){
            val pick = new PickEntity();
            pick.setAnswerId(request.getAnswerId());
            pick.setTextAnswer(request.getTextAnswer());

            val vote = voteRepo.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
            pick.setVote(vote);
            pickRepo.save(pick);

            return pick;
    }
}
