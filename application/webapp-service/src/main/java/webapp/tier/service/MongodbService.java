package webapp.tier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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

	public boolean connectionStatus() {
		boolean status = false;
		try {
			getCollection();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public String insertMsg() throws Exception {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		try {
			Document document = new Document()
					.append("id", msgbean.getIdString())
					.append("msg", msgbean.getMessage());
			getCollection().insertOne(document);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Insert Error.");
		}
		msgbean.setFullmsgWithType(msgbean, "Insert");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public List<String> selectMsg() throws Exception {
		MongoCursor<Document> cursor = null;
		List<String> msglist = new ArrayList<>();

		try {
			cursor = getCollection().find().iterator();
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

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Select Error.");
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return msglist;
	}

	@Override
	public String deleteMsg() throws Exception {
		try {
			getCollection().drop();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Delete Error.");
		}
		return "Delete Msg Collection";
	}

	private MongoCollection<Document> getCollection() {
		return mongoClient.getDatabase(dbname).getCollection(collectionname);
	}
}
