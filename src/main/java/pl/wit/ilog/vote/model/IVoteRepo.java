package pl.wit.ilog.vote.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVoteRepo extends JpaRepository<VoteEntity, Long> {

    // how many times a given answer was selected
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerPickCount(p.answerId, count(p.id)) FROM PickEntity p WHERE p.answerId = :answerId")
    AnswerPickCount answerIdPicksAmount(@Param("answerId") Long answerId);

    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerPickCount(p.answerId, count(p.id)) FROM PickEntity p GROUP BY p.answerId")
    List<AnswerPickCount> allAnswerIdsPicksAmount();

    // how many people of each age voted
    @Query("SELECT NEW pl.wit.ilog.vote.model.AgePickCount(v.age, count(v.age)) FROM VoteEntity v WHERE v.age = :age")
    AgePickCount allAgePicksAmount(@Param("age") Integer age);

    @Query("SELECT NEW pl.wit.ilog.vote.model.AgePickCount(v.age, count(v.age)) FROM VoteEntity v GROUP BY v.age")
    List<AgePickCount> allAgesPicksAmount();

    // how many people of each sex voted
    @Query("SELECT NEW pl.wit.ilog.vote.model.SexPickCount(v.sex, count(v.sex)) FROM VoteEntity v WHERE v.sex = :sex")
    SexPickCount allSexPicksAmount(@Param("sex") SexEnum sex);

    @Query("SELECT NEW pl.wit.ilog.vote.model.SexPickCount(v.sex, count(v.sex)) FROM VoteEntity v GROUP BY v.sex")
    List<SexPickCount> allSexesPicksAmount();

    // how many people of each sex chose a particular answer



    // =============== TEXT ANSWERS ===============


}
