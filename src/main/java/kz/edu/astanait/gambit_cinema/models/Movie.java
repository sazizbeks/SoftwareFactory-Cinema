package kz.edu.astanait.gambit_cinema.models;

import kz.edu.astanait.gambit_cinema.validation.ValidationMarkers;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Set;

@Entity
@Table(name = "movies")
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {ValidationMarkers.OnUpdate.class,
            ValidationMarkers.OnDelete.class},
            message = "Id cannot be null")
    private Long id;

    @NotBlank(groups = {ValidationMarkers.OnCreate.class,
            ValidationMarkers.OnUpdate.class},
            message = "Name cannot be empty")
    private String name;

    @NotNull(groups = {ValidationMarkers.APIOnCreate.class,
            ValidationMarkers.APIOnUpdate.class},
            message = "Release date cannot be empty")
    @Column(columnDefinition = "date")
    private Calendar releaseDate;

    @NotBlank(groups = {ValidationMarkers.OnCreate.class,
            ValidationMarkers.OnUpdate.class},
            message = "Director cannot be empty")
    private String director;

    @NotBlank(groups = {ValidationMarkers.OnCreate.class,
            ValidationMarkers.OnUpdate.class},
            message = "Actors cannot be empty")
    private String actors;

    @NotNull(groups = {ValidationMarkers.OnCreate.class,
            ValidationMarkers.OnUpdate.class},
            message = "Age rating cannot be empty")
    private Byte ageRating;

    @NotNull(groups = {ValidationMarkers.OnCreate.class,
            ValidationMarkers.OnUpdate.class},
            message = "Image URL cannot be empty")
    @URL(groups = {ValidationMarkers.OnCreate.class,
            ValidationMarkers.OnUpdate.class},
            message = "This must be URL")
    @Column(length = 8192)
    private String imgUrl;

    @NotNull(groups = {ValidationMarkers.OnCreate.class,
            ValidationMarkers.OnUpdate.class},
            message = "Description cannot be empty")
    @Column(length = 1024)
    private String description;

    @ManyToMany
    private Set<Genre> genres;

    @NotNull(groups = {ValidationMarkers.OnCreate.class,
            ValidationMarkers.OnUpdate.class},
            message = "Trailer URL cannot be empty")
    @URL(groups = {ValidationMarkers.OnCreate.class,
            ValidationMarkers.OnUpdate.class},
            message = "This must be URL")
    private String trailerUrl;

    public Movie() {
    }
}