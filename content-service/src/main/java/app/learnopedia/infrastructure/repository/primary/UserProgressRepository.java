package app.learnopedia.infrastructure.repository.primary;

import app.learnopedia.domain.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, UUID> {

    List<UserProgress> findByUserId(String userId);
}
