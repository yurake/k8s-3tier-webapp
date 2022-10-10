package webapp.tier.db.mysql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="msg")
@Getter
@Setter
public class MysqlMessage {

    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String msg;

}
