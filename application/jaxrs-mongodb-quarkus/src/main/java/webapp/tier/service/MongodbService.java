package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;

@ApplicationScoped
public class MongodbService {

	@Inject
	MongoClient mongoClient;

	@ConfigProperty(name = "common.message")
	String message;
	@ConfigProperty(name = "mongodb.db.name")
	String dbname;
	@ConfigProperty(name = "mongodb.collection.name")
	String collectionname;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public MsgBean insertMsg(MongoCollection<Document> collection) throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Insert");
		Document document = new Document()
				.append("id", msgbean.getId())
				.append("msg", msgbean.getMessage());
		collection.insertOne(document);
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	public List<MsgBean> selectMsg(MongoCollection<Document> collection) {
		List<MsgBean> msglist = new ArrayList<>();

		try (MongoCursor<Document> cursor = collection.find().iterator()) {
			while (cursor.hasNext()) {
				Document document = cursor.next();
				MsgBean msgbean = new MsgBean(document.getInteger("id"), document.getString("msg"),
						"Select");
				logger.log(Level.INFO, msgbean.getFullmsg());
				msglist.add(msgbean);
			}
		} finally {
			if (msglist.isEmpty()) {
				msglist.add(new MsgBean(0, "No Data.", "Select"));
			}
		}
		return msglist;
	}

	public String deleteMsg(MongoCollection<Document> collection) {
		String msg = "Delete Msg Collection";
		collection.drop();
		logger.log(Level.INFO, msg);
		return msg;
	}

	public MongoCollection<Document> getCollection() {
		return mongoClient.getDatabase(dbname).getCollection(collectionname);
	}
}
