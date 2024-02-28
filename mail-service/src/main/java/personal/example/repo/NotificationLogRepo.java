package personal.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.example.model.NotificationLog;

@Repository
public interface NotificationLogRepo extends JpaRepository<NotificationLog, String> {
}
