package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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

import webapp.tier.interfaces.Database;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

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
	public String insertMsg() throws NoSuchAlgorithmException {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		Document document = new Document()
				.append("id", msgbean.getIdString())
				.append("msg", msgbean.getMessage());
		try {
			getCollection().insertOne(document);
		} catch (NullPointerException e) {
			LOG.log(Level.SEVERE, "Insert Error.", e);
			throw new NullPointerException("Insert Error.");
		} catch (MongoException e) {
			LOG.log(Level.SEVERE, "Insert Error.", e);
			throw new RuntimeException("Insert Error.");
		}
		msgbean.setFullmsgWithType(msgbean, "Insert");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public List<String> selectMsg() throws SQLException {
		List<String> msglist = new ArrayList<>();

		try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
			while (cursor.hasNext()) {
				Document document = cursor.next();
				MsgBeanUtils msgbean = new MsgBeanUtils();
				msgbean.setIdString(document.getString("id"));
				msgbean.setMessage(document.getString("msg"));
				msgbean.setFullmsgWithType(msgbean, "Select");
				LOG.info(msgbean.getFullmsg());
				msglist.add(msgbean.getFullmsg());
			}
			if (msglist.isEmpty()) {
				msglist.add("No Data");
			}
		} catch (NullPointerException e) {
			LOG.log(Level.SEVERE, "Select Error.", e);
			throw new SQLException("Select Error.");
		} catch (MongoException e) {
			LOG.log(Level.SEVERE, "Select Error.", e);
			throw new RuntimeException("Select Error.");
		}
		return msglist;
	}

	@Override
	public String deleteMsg() {
		try {
			getCollection().drop();
		} catch (NullPointerException e) {
			LOG.log(Level.SEVERE, "Delete Error.", e);
			throw new NullPointerException("Delete Error.");
		} catch (MongoException e) {
			LOG.log(Level.SEVERE, "Delete Error.", e);
			throw new RuntimeException("Delete Error.");
		}
		return "Delete Msg Collection";
	}

	private MongoCollection<Document> getCollection() {
		return mongoClient.getDatabase(dbname).getCollection(collectionname);
	}
}
