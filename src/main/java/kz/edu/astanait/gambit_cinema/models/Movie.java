package kz.edu.astanait.gambit_cinema.models;

import kz.edu.astanait.gambit_cinema.validation.ValidationMarkers;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "movies")
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

    @NotNull(groups = {ValidationMarkers.OnCreate.class,
            ValidationMarkers.OnUpdate.class},
    message = "Release date cannot be empty")
    private Date releaseDate;

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

    @ManyToMany
    private Set<Genre> genres;

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public Byte getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(Byte ageRating) {
        this.ageRating = ageRating;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", ageRating=" + ageRating +
                ", genres=" + genres +
                '}';
    }
}