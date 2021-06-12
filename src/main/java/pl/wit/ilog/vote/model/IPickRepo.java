package pl.wit.ilog.vote.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPickRepo extends JpaRepository<PickEntity, Long> {
}
