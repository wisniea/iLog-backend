package pl.wit.ilog.vote.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/query")
@AllArgsConstructor
public class OrderController {

    private final IVoteRepo voteRepo;

    /*
    @GetMapping("/allAnswerPicks")
    public List<AnswerPickCount> allAnswerIdsPicksAmount(){
        return voteRepo.allAnswerIdsPicksAmount();
    }
*/
//    @GetMapping("/answerPicks/{answerId}")
//    public AnswerPickCount answerIdPickAmount(@Valid @PathVariable("answerId") final Long answerId){
//        return voteRepo.answerIdPicksAmount(answerId);
//    }

    /*
    @GetMapping("/picksByAges")
    public List<AgePickCount> allAgesPicksAmount(){
        return voteRepo.allAgesPicksAmount();
    }

    /*
    @GetMapping("/picksByAge/{age}")
    public AgePickCount allAgePicksAmount(@Valid @PathVariable("age") final Integer age){
        return voteRepo.allAgePicksAmount(age);
    }

    @GetMapping("/picksBySexes")
    public List<SexPickCount> allSexesPicksAmount(){
        return voteRepo.allSexesPicksAmount();
    }

    @GetMapping("/picksBySex/{sex}")
    public SexPickCount allSexPicksAmount(@Valid @PathVariable("sex") final SexEnum sex){
        return voteRepo.allSexPicksAmount(sex);
    }
*/
}
