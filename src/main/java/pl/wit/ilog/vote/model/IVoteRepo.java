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

    // returns gender metrics for all forms (answerId -> gender metrics -> count)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerGenderCount(p.answerId, v.gender, count(v.gender)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON v.form.id = f.id " +
            "GROUP BY p.answerId, v.gender")
    List<AnswerGenderCount> globalAnswerIdGenderMetrics();

    // returns gender metrics for specific form (answerId -> gender metrics -> count)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerGenderCount(p.answerId, v.gender, count(v.gender)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON v.form.id = f.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY p.answerId, v.gender")
    List<AnswerGenderCount> answerIdGenderMetrics(@Param("uuid") @NotNull final UUID uuid);

    // how many people of each age selected each answer
    // returns age metrics for all forms (answerId -> age metrics -> count)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AgePickCount(p.answerId, v.age, count(v.age)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON v.form.id = f.id " +
            "GROUP BY p.answerId, v.age")
    List<AgePickCount> globalAnswerIdAgesMetrics();

    // returns age metrics for specific form (answerId -> age metrics -> count)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AgePickCount(p.answerId, v.age, count(v.age)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON v.form.id = f.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY p.answerId, v.age")
    List<AgePickCount> answerIdAgesMetrics(@Param("uuid") @NotNull final UUID uuid);

    //  return votes from all forms
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerPickCount(p.answerId, count(p.answerId)) FROM PickEntity p " +
            "GROUP BY p.answerId")
    List<AnswerPickCount> globalAnswerIdPicksCount();

    // return votes from specific form (answerId - votes)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerPickCount(p.answerId, count(p.answerId)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY p.answerId")
    List<AnswerPickCount> answerIdPicksCount(@Param("uuid") @NotNull final UUID uuid);

    // returns gender and age metrics (all forms - global metrics))
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerAgeGenderCount(p.answerId, v.gender, v.age, count(v.gender)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "GROUP BY p.answerId, v.gender, v.age")
    List<AnswerAgeGenderCount> globalAnswerIdGenderAgesCount();

    // returns gender and age metrics (specific form)
    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerAgeGenderCount(p.answerId, v.gender, v.age, count(v.gender)) FROM PickEntity p " +
            "LEFT JOIN VoteEntity v ON p.vote.id = v.id " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY p.answerId, v.gender, v.age")
    List<AnswerAgeGenderCount> answerIdGenderAgesCount(@Param("uuid") @NotNull final UUID uuid);

    // how many people of each gender voted (specific form)
    @Query("SELECT NEW pl.wit.ilog.vote.model.GenderCount(v.gender, count(v.gender)) FROM VoteEntity v " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY v.gender")
    List<GenderCount> genderFormCount(@Param("uuid") @NotNull final UUID uuid);

    // how many people of each gender voted (all forms - global metrics)
    @Query("SELECT NEW pl.wit.ilog.vote.model.GenderCount(v.gender, count(v.gender)) FROM VoteEntity v " +
            "GROUP BY v.gender")
    List<GenderCount> globalGenderCount();

    // what age range people filled in the poll
    @Query("SELECT NEW pl.wit.ilog.vote.model.AgeCount(v.age, count(v.age)) FROM VoteEntity v " +
            "LEFT JOIN FormEntity f ON f.id = v.form.id " +
            "WHERE f.uuid = :uuid " +
            "GROUP BY v.age")
    List<AgeCount> ageFormCount(@Param("uuid") @NotNull final UUID uuid);

    // what age range people filled in the polls (all forms - global metrics)
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


    @Query("SELECT NEW pl.wit.ilog.vote.model.QuestionText(q.question) FROM AnswerEntity a " +
            "LEFT JOIN QuestionEntity q ON a.question.id = q.id " +
            "LEFT JOIN FormEntity f ON f.id = q.form.id " +
            "WHERE f.uuid = :uuid AND a.id = :answerId")
    QuestionText getQuestionTextByAnswerId(@Param("uuid") @NotNull final UUID uuid,
                                     @Param("answerId") Long answerId);

}
