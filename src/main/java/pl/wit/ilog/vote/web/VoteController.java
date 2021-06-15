package pl.wit.ilog.vote.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
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
        vote.setGender(request.getGender());
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

    // return answerId = null, count = 0 if answer amount = 0, else return answerId = id, amount = {number > 0}
    @PreAuthorize("hasRole('USER')")
    @GetMapping("{formUuid}/insights/{answerId}")
    List<AnswerPickCount> getAllByAnswerId(@PathVariable(name = "formUuid") @NotNull UUID formUuid,
                                           @PathVariable(name = "answerId") @NotNull Long answerId){
        return voteRepo.allAnswerIdPicksAmount(answerId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("{uuid}/gendersMetrics")
    List<AnswerGenderCount> answerIdGendersMetrics(@PathVariable @NotNull final UUID uuid){
        return voteRepo.answerIdGenderMetrics(uuid);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/globalGendersMetrics")
    List<AnswerGenderCount> globalAnswerIdGenderMetrics(){
        return voteRepo.globalAnswerIdGenderMetrics();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("{uuid}/agesMetrics")
    List<AgePickCount> answerIdAgesMetrics(@PathVariable @NotNull final UUID uuid){
        return voteRepo.answerIdAgesMetrics(uuid);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/globalAgesMetrics")
    List<AgePickCount> globalAnswerIdAgesMetrics(){
        return voteRepo.globalAnswerIdAgesMetrics();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("{uuid}/votes")
    List<AnswerPickCount> answerIpPicksCount(@PathVariable @NotNull final UUID uuid){
        return voteRepo.answerIdPicksCount(uuid);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/globalVotes")
    List<AnswerPickCount> globalAnswerIdPicksCount(){
        return voteRepo.globalAnswerIdPicksCount();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("{uuid}/gendersAgesMetrics")
    List<AnswerAgeGenderCount> answerIdGenderAgesCount(@PathVariable @NotNull final UUID uuid){
        return voteRepo.answerIdGenderAgesCount(uuid);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/globalGendersAgesMetrics")
    List<AnswerAgeGenderCount> globalAnswerIdGenderAgesCount(){
        return voteRepo.globalAnswerIdGenderAgesCount();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("{uuid}/genderFormCount")
    List<GenderCount> genderFormCount(@PathVariable @NotNull final UUID uuid) {
        return voteRepo.genderFormCount(uuid);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/globalGenderCount")
    List<GenderCount> globalGenderCount(){
        return voteRepo.globalGenderCount();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("{uuid}/ageFormCount")
    List<AgeCount> ageFormCount(@PathVariable @NotNull final UUID uuid) {
        return voteRepo.ageFormCount(uuid);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/globalAgeCount")
    List<AgeCount> globalAgeCount(){
        return voteRepo.globalAgeCount();
    }

    // TEXT ANSWERS
    @PreAuthorize("hasRole('USER')")
    @GetMapping("{uuid}/textAnswers")
    List<TextAnswer> allTextAnswersFromForm(@PathVariable @NotNull final UUID uuid){
        return voteRepo.allTextAnswersFromForm(uuid);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("{uuid}/textAnswers/{answerId}")
    List<TextAnswer> allTextAnswersForSpecificAnswer(@PathVariable @NotNull final UUID uuid,
                                                     @PathVariable Long answerId){
        return voteRepo.allTextAnswersForSpecificAnswer(uuid, answerId);
    }

}
