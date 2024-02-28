package personal.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.example.model.UserRanking;

@Repository
public interface UserRankingRepo extends JpaRepository<UserRanking, Integer> {
}
