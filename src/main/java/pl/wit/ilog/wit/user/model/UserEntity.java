package pl.wit.ilog.wit.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Cacheable
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_to_role",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(name = "uk_user_to_role_user_id_role", columnNames={"user_id", "role"})
    )
    @Column(name="role")
    private Set<RoleEnum> roles = new HashSet<>();

    // user has a list of polls
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    private List<PollEntity> polls = new ArrayList<>();
}
