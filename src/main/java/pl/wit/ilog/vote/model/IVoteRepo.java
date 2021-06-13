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
    List<AnswerPickCount> allAnswerIdPicksAmount(@Param("answerId") Long answerId);

   /* @Query("SELECT NEW pl.wit.ilog.vote.model.AgePickCount(v.age, count(v.age)) FROM VoteEntity v WHERE v.age = :age")
    List<AgePickCount> answerIdPicksByAgeAmount(@Param("answerId") Long answerId);*/

    List<VoteEntity> findAllByAge(AgeEnum age);
   /* @Deprecated
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerPickCount(p.answerId, count(p.id)) FROM PickEntity p WHERE p. GROUP BY p.answerId")
    List<AnswerPickCount> allAnswerIdsPicksAmount(@Param("formId") Long formId);*/

    // how many people of each age voted
  /*  @Deprecated
    @Query("SELECT NEW pl.wit.ilog.vote.model.AgePickCount(v.age, count(v.age)) FROM VoteEntity v WHERE v.age = :age")
    AgePickCount allAgePicksAmount(@Param("age") Integer age);*/

  /*
    @Query("SELECT NEW pl.wit.ilog.vote.model.AgePickCount(v., count(v.age)) FROM VoteEntity v GROUP BY v.age")
    List<AgePickCount> allAgesPicksAmount(@Param("formUuid") UUID formUuid);

    // how many people of each sex voted
    @Query("SELECT NEW pl.wit.ilog.vote.model.SexPickCount(v.sex, count(v.sex)) FROM VoteEntity v WHERE v.sex = :sex")
    SexPickCount allSexPicksAmount(@Param("sex") SexEnum sex);

    @Query("SELECT NEW pl.wit.ilog.vote.model.SexPickCount(v.sex, count(v.sex)) FROM VoteEntity v GROUP BY v.sex")
    List<SexPickCount> allSexesPicksAmount();
    */ //temp

    //List<AgePickCount> findAllByAgeAndPicks

    // how many people of each sex chose a particular answer
    /*@Query("SELECT NEW pl.wit.ilog.vote.model.AnswerSexCount() FROM AnswerEntity a " +
            "LEFT JOIN QuestionEntity ON AnswerEntity.question = QuestionEntity.id " +
            "LEFT JOIN FormEntity ON FormEntity.id = QuestionEntity.form +" +
            "LEFT JOIN VoteEntity ON FormEntity.id = VoteEntity.form" +
            "WHERE ")
    AnswerSexCount answerSexMetrics(@Param("answerId") Long answerId, @Param("sex") SexEnum sex);*/

/*
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerSexCount(AnswerEntity.id, VoteEntity.sex, count(VoteEntity.sex)) FROM AnswerEntity a " +
            "LEFT JOIN QuestionEntity q ON a.question = q " +
            "LEFT JOIN FormEntity f ON f = q.form " +
            "LEFT JOIN VoteEntity v ON f = v.form GROUP BY a.id, v.sex")
    List<AnswerSexCount> answerIdSexesMetrics();
*/
    // how many people of each age selected answer


    // =============== TEXT ANSWERS ===============


}
