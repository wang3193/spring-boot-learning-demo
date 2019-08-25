package com.samwang.sys;

        import lombok.Data;
        import org.springframework.data.domain.Persistable;
        import org.springframework.data.jpa.domain.AbstractAuditable;
        import org.springframework.data.jpa.domain.support.AuditingEntityListener;

        import javax.persistence.*;

@Data
@Entity
@Table(name="t_user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String name;

}
