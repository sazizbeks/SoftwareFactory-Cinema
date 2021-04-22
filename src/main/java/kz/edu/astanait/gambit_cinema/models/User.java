package kz.edu.astanait.gambit_cinema.models;

import kz.edu.astanait.gambit_cinema.validation.ValidationMarkers;
import lombok.*;
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
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "favorite_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> favoriteMovies;

    public User() {
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = role.getAuthorities().stream()
                .map(a -> new SimpleGrantedAuthority(a.getName()))
                .collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return grantedAuthorities;
    }

}
