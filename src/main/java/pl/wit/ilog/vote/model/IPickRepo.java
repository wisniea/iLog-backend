package pl.wit.ilog.vote.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPickRepo extends JpaRepository<PickEntity, Long> {
    List<PickEntity> findAllByAnswerId(Long id);
}
