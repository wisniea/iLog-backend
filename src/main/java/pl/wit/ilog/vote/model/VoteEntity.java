package pl.wit.ilog.vote.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.wit.ilog.form.model.FormEntity;

import javax.persistence.*;

@Table(name = "vote")
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "ip", nullable = false)
    private String ip;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "poll_id", nullable = false)
    private FormEntity form;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private SexEnum sex;

    @Column(name = "age", nullable = false)
    private Integer age;

}
