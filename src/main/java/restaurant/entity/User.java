package restaurant.entity;

import jakarta.validation.constraints.Email;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import restaurant.entity.enums.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq",allocationSize = 1,initialValue = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name", nullable = false)
    @NotBlank
    @NotNull
    @NotEmpty
    private String firstName;
    @Column(name = "last_name", nullable = false)
    @NotNull
    private String lastName;
    @Column(name = "date_of_birth", nullable = false)
    @NotNull
    private LocalDate dateOfBirth;
    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotNull
    private String email;
    @Column(name = "password", nullable = false)
    @NotNull
    private String password;
    @Column(name = "phone_number", nullable = false, unique = true)
    @NotNull
    @NumberFormat
    private String phoneNumber;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;
    @Column(name = "experience", nullable = false)
    @NotNull
    private int experience;
    @Transient
    private Boolean isChecked;
    @OneToMany(cascade = {PERSIST,REFRESH,DETACH,MERGE,REMOVE},fetch = LAZY,mappedBy = "user")
    private List<Cheque> cheque = new ArrayList<>();
    @ManyToOne(cascade = {PERSIST,REFRESH,DETACH,MERGE},fetch = EAGER)
    private Restaurant restaurant;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}