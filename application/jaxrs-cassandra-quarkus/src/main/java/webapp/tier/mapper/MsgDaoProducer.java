package webapp.tier.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import com.datastax.oss.quarkus.runtime.api.session.QuarkusCqlSession;

import webapp.tier.dao.MsgDao;

public class MsgDaoProducer {

	private final MsgDao msgDao;

	@Inject
	public MsgDaoProducer(QuarkusCqlSession session) {
		MsgMapper mapper = new MsgMapperBuilder(session).build();
		msgDao = mapper.msgDao();
	}

	@Produces
	@ApplicationScoped
	MsgDao produceMsgDao() {
		return msgDao;
	}
}
