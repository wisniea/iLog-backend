package pl.wit.ilog.form.model;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wit.ilog.user.model.UserEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface IFormRepo extends JpaRepository<FormEntity, Long> {
    Optional<FormEntity> findByUuid(UUID uuid);
    Stream<FormEntity> findAllByCreatedBy(UserEntity user);
}
