package kz.edu.astanait.gambit_cinema.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "feedbacks")
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Movie movie;

    private String comment;

    public Feedback() {
    }
}
