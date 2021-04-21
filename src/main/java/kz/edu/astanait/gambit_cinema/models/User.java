package kz.edu.astanait.gambit_cinema.models;

import kz.edu.astanait.gambit_cinema.validation.ValidationMarkers;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = {ValidationMarkers.OnRegistration.class, ValidationMarkers.OnLogin.class},
            message = "Username cannot be empty")
    @Column(unique = true)
    private String username;

    @NotBlank(groups = {ValidationMarkers.OnRegistration.class, ValidationMarkers.OnLogin.class},
            message = "Password cannot be empty")
    @Length(groups = {ValidationMarkers.OnRegistration.class},
            min = 7, max = 32, message = "Minimum length of password is 7, maximum 32")
    private String password;

    @Transient
    @NotBlank(groups = ValidationMarkers.OnRegistration.class,
            message = "Password confirmation cannot be empty")
    private String rePassword;

    @NotBlank(groups = ValidationMarkers.OnRegistration.class,
            message = "Email cannot be empty")
    @Email(groups = ValidationMarkers.OnRegistration.class,
            message = "Wrong email")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(columnDefinition = "date")
    private Date birthDate;

    public User() {
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = role.getAuthorities().stream()
                .map(a -> new SimpleGrantedAuthority(a.getName()))
                .collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return grantedAuthorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", birthDate=" + birthDate +
                '}';
    }
}
