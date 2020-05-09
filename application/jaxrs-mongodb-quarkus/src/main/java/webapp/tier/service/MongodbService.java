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

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import webapp.tier.bean.MsgBean;
import webapp.tier.interfaces.Database;
import webapp.tier.util.CreateId;

@ApplicationScoped
public class MongodbService implements Database {

	@Inject
	MongoClient mongoClient;

	@ConfigProperty(name = "common.message")
	String message;
	@ConfigProperty(name = "mongodb.db.name")
	String dbname;
	@ConfigProperty(name = "mongodb.collection.name")
	String collectionname;

	private static final Logger LOG = Logger.getLogger(MongodbService.class.getSimpleName());

	@Override
	public MsgBean insertMsg() throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Insert");
		Document document = new Document()
				.append("id", msgbean.getId())
				.append("msg", msgbean.getMessage());
		try {
			getCollection().insertOne(document);
		} catch (NullPointerException | MongoException e) {
			LOG.log(Level.SEVERE, "Insert Error.", e);
			throw new RuntimeException("Insert Error.");
		}
		LOG.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public List<MsgBean> selectMsg() throws RuntimeException {
		List<MsgBean> msglist = new ArrayList<>();

		try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
			while (cursor.hasNext()) {
				Document document = cursor.next();
				MsgBean msgbean = new MsgBean(document.getInteger("id"), document.getString("msg"), "Select");
				LOG.log(Level.INFO, msgbean.getFullmsg());
				msglist.add(msgbean);
			}
			if (msglist.isEmpty()) {
				msglist.add(new MsgBean(0, "No Data.", "Select"));
			}
		} catch (NullPointerException | MongoException e) {
			LOG.log(Level.SEVERE, "Select Error.", e);
			throw new RuntimeException("Select Error.");
		}
		return msglist;
	}

	@Override
	public String deleteMsg() throws RuntimeException {
		String msg = "Delete Msg Collection";
		try {
			getCollection().drop();
			LOG.log(Level.INFO, msg);
		} catch (NullPointerException | MongoException e) {
			LOG.log(Level.SEVERE, "Delete Error.", e);
			throw new RuntimeException("Delete Error.");
		}
		return msg;
	}

	private MongoCollection<Document> getCollection() {
		return mongoClient.getDatabase(dbname).getCollection(collectionname);
	}
}
