package pl.wit.ilog.form.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IFormRepo extends JpaRepository<FormEntity, Long> {
    Optional<FormEntity> findByUuid(UUID uuid);
}
