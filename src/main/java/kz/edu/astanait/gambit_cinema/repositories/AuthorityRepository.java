package kz.edu.astanait.gambit_cinema.repositories;

import kz.edu.astanait.gambit_cinema.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
