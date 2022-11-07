package mx.luisferrr.security.business.domain;

import lombok.*;
import mx.luisferrr.security.business.enums.AuditFlag;
import mx.luisferrr.security.business.enums.UserStatus;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "role", schema = "sec")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "job_position")
    private String jobPosition;

    @ManyToOne
    @JoinColumn(name = "main_role_id")
    private Role mainRole;

    @Column(name = "email_confirmed")
    private Integer emailConfirmed;

    @Column(name = "account_expiration_date")
    private Date accountExpirationDate;

    @Column(name = "credentials_expiration_date")
    private Date credentialsExpirationDate;

    @Column(name = "status")
    private UserStatus status;

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

    @Transient
    private List<GrantedAuthority> grantedAuthorityList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        Date currentDate = new Date();
        return currentDate.compareTo(accountExpirationDate) <= 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != UserStatus.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        Date currentDate = new Date();
        return currentDate.compareTo(credentialsExpirationDate) <= 0;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }
}
