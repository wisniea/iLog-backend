package pl.wit.ilog.vote.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@Repository
public interface IVoteRepo extends JpaRepository<VoteEntity, Long> {

    // how many times a given answer was selected
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerPickCount(p.answerId, count(p.id)) FROM PickEntity p WHERE p.answerId = :answerId")
    List<AnswerPickCount> allAnswerIdPicksAmount(@Param("answerId") Long answerId);

    // returns gender metrics for all forms (answerId -> metrics)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerSexCount(p.answerId, v.sex, count(v.sex)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON v.form.id = f.id " +
            "GROUP BY p.answerId, v.sex")
    List<AnswerSexCount> globalAnswerIdSexesMetrics();

    // returns gender metrics for specific form (answerId -> metrics)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerSexCount(p.answerId, v.sex, count(v.sex)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON v.form.id = f.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY p.answerId, v.sex")
    List<AnswerSexCount> answerIdSexesMetrics(@Param("uuid") @NotNull final UUID uuid);

    // how many people of each age selected each answer
    // returns age metrics for all forms (answerId -> metrics)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AgePickCount(p.answerId, v.age, count(v.age)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON v.form.id = f.id " +
            "GROUP BY p.answerId, v.age")
    List<AgePickCount> globalAnswerIdAgesMetrics();

    // returns age metrics for specific form (answerId -> metrics)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AgePickCount(p.answerId, v.age, count(v.age)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON v.form.id = f.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY p.answerId, v.age")
    List<AgePickCount> answerIdAgesMetrics(@Param("uuid") @NotNull final UUID uuid);

    //  return votes
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerPickCount(p.answerId, count(p.answerId)) FROM PickEntity p " +
            "GROUP BY p.answerId")
    List<AnswerPickCount> globalAnswerIdPicksCount();

    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerPickCount(p.answerId, count(p.answerId)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY p.answerId")
    List<AnswerPickCount> answerIpPicksCount(@Param("uuid") @NotNull final UUID uuid);

    // returns sex and age metrics
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerAgeSexCount(p.answerId, v.sex, v.age, count(v.sex)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "GROUP BY p.answerId, v.sex, v.age")
    List<AnswerAgeSexCount> globalAnswerIdSexesAgesCount();

    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerAgeSexCount(p.answerId, v.sex, v.age, count(v.sex)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY p.answerId, v.sex, v.age")
    List<AnswerAgeSexCount> answerIdSexesAgesCount(@Param("uuid") @NotNull final UUID uuid);

    // how many people of each sex voted (in all form)
    @Query("SELECT NEW pl.wit.ilog.vote.model.SexCount(v.sex, count(v.sex)) FROM VoteEntity v " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY v.sex")
    List<SexCount> sexFormCount(@Param("uuid") @NotNull final UUID uuid);

    @Query("SELECT NEW pl.wit.ilog.vote.model.SexCount(v.sex, count(v.sex)) FROM VoteEntity v " +
            "GROUP BY v.sex")
    List<SexCount> globalSexCount();

    // what age range people filled in the poll
    @Query("SELECT NEW pl.wit.ilog.vote.model.AgeCount(v.age, count(v.age)) FROM VoteEntity v " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY v.age")
    List<AgeCount> ageFormCount(@Param("uuid") @NotNull final UUID uuid);

    @Query("SELECT NEW pl.wit.ilog.vote.model.AgeCount(v.age, count(v.age)) FROM VoteEntity v " +
            "GROUP BY v.age")
    List<AgeCount> globalAgeCount();

    // =============== TEXT ANSWERS ===============
    // all text answers from form
    @Query("SELECT NEW pl.wit.ilog.vote.model.TextAnswer(p.answerId, p.textAnswer) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "WHERE p.textAnswer IS NOT NULL AND f.uuid = :uuid")
    List<TextAnswer> allTextAnswersFromForm(@Param("uuid") @NotNull final UUID uuid);

    // all text answers from specific answer
    @Query("SELECT NEW pl.wit.ilog.vote.model.TextAnswer(p.answerId, p.textAnswer) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "WHERE p.textAnswer IS NOT NULL AND f.uuid = :uuid AND p.answerId = :answerId")
    List<TextAnswer> allTextAnswersForSpecificAnswer(@Param("uuid") @NotNull final UUID uuid,
                                                     @Param("answerId") Long answerId);

    // all text answers from


    /////////////////// DEPRECATED ///////////////////

    // OLD
    // returns gender metrics for all forms (answerId -> metrics)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerSexCount(a.id, v.sex, count(v.sex)) FROM AnswerEntity a " +
            "LEFT JOIN QuestionEntity q ON a.question.id = q.id " +
            "LEFT JOIN FormEntity f ON q.form.id = f.id " +
            "LEFT JOIN VoteEntity v ON v.form.id = f.id " +
            "GROUP BY a.id, v.sex")
    List<AnswerSexCount> globalAnswerIdSexesMetricsOLD();

    // OLD
    // returns gender metrics for specific form (answerId -> metrics)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerSexCount(a.id, v.sex, count(v.sex)) FROM AnswerEntity a " +
            "LEFT JOIN QuestionEntity q ON a.question.id = q.id " +
            "LEFT JOIN FormEntity f ON q.form.id = f.id " +
            "LEFT JOIN VoteEntity v ON v.form.id = f.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY a.id, v.sex")
    List<AnswerSexCount> answerIdSexesMetricsOLD(@Param("uuid") @NotNull final UUID uuid);





   /* @Query("SELECT NEW pl.wit.ilog.vote.model.AgePickCount(v.age, count(v.age)) FROM VoteEntity v WHERE v.age = :age")
    List<AgePickCount> answerIdPicksByAgeAmount(@Param("answerId") Long answerId);*/

    //List<VoteEntity> findAllByAge(AgeEnum age);
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









}
