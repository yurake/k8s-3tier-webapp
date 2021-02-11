package webapp.tier.db.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlMessageRepository extends JpaRepository<MysqlMessage, Integer> {

}
