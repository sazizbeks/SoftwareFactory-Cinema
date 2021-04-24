package kz.edu.astanait.gambit_cinema.repositories;

import kz.edu.astanait.gambit_cinema.models.Movie;
import kz.edu.astanait.gambit_cinema.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
