package pl.wit.ilog.user.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "username",unique = true, nullable = false)
    private String username;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_to_role",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(name = "uk_user_to_role_user_id_role", columnNames={"user_id", "role"})
    )
    @Column(name="role")
    private Set<RoleEnum> roles = new HashSet<>();
}
