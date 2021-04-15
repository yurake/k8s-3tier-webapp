package webapp.tier.db.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresMessageRepository extends JpaRepository<PostgresMessage, Integer> {

}
