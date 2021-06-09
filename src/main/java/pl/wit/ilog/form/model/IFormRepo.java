package pl.wit.ilog.form.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface IFormRepo extends CrudRepository<FormEntity, Long> {
    Optional<FormEntity> findByUuid(UUID uuid);
}
