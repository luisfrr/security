package mx.luisferrr.security.business.domain;

import lombok.*;
import mx.luisferrr.security.business.enums.AccessStatus;
import mx.luisferrr.security.business.enums.AuditFlag;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_controller", schema = "sec",
        uniqueConstraints= {
                @UniqueConstraint(columnNames = {"user_id", "controller_id"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserController {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "controller_id")
    private Controller controller;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "access_status", nullable = false)
    private AccessStatus accessStatus;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "audit_flag")
    private AuditFlag auditFlag;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_at")
    private Date lastModifiedAt;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

}
