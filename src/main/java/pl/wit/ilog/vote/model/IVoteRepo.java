package pl.wit.ilog.vote.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IVoteRepo extends JpaRepository<VoteEntity, Long> {
}
