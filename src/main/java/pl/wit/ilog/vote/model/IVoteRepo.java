package pl.wit.ilog.vote.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVoteRepo extends JpaRepository<VoteEntity, Long> {

    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerPickCount(p.answerId, count(p.id)) FROM PickEntity p WHERE p.answerId = :answerId GROUP BY p.answerId")
    AnswerPickCount countByPickEntityIdGroupByAnswerId(@Param("answerId") Long answerId);

    @Query("SELECT NEW pl.wit.ilog.vote.model.AnswerPickCount(p.answerId, count(p.id)) FROM PickEntity p GROUP BY p.answerId")
    List<AnswerPickCount> countByPickEntityIdGroupByAnswerId();
}
