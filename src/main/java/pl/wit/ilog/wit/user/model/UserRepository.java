package pl.wit.ilog.wit.user.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.Repository;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface UserRepository extends Repository<UserEntity, Long> {
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<UserEntity> findByUsername(String username);

    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<UserEntity> findById(Long id);

    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select u from UserEntity u where u.id = :#{#security.principal.id}")
    Optional<UserEntity> findCurrent();

    @Modifying(clearAutomatically = true)
    UserEntity save(UserEntity u);

    @Modifying(clearAutomatically = true)
    void delete(UserEntity u);
}
