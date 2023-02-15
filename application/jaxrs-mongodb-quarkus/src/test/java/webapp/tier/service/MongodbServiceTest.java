package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

@QuarkusTest
class MongodbServiceTest {

	@Inject
	MongodbService mongosvc;

	private String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@Test
	@SuppressWarnings("unchecked")
	void testInsertMysql() throws NoSuchAlgorithmException {
		MongoCollection<Document> col = Mockito.mock(MongoCollection.class);
		when(col.insertOne(ArgumentMatchers.any())).thenReturn(null);
		assertThat(mongosvc.insertMsg(col).getFullmsg(), containsString(respbody));
	}

	@Test
	@SuppressWarnings("unchecked")
	void testSelectMsgIsEmpty() {
		MongoCursor<Document> cursor = Mockito.mock(MongoCursor.class);
		FindIterable<Document> ite = Mockito.mock(FindIterable.class);
		MongoCollection<Document> col = Mockito.mock(MongoCollection.class);
		when(col.find()).thenReturn(ite);
		when(ite.iterator()).thenReturn(cursor);
		mongosvc.selectMsg(col);
	}

	@Test
	@SuppressWarnings("unchecked")
	void testSelectMsgWithMessage() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", 1111);
		map.put("msg", "Test");
		Document document = new Document(map);
		MongoCursor<Document> cursor = Mockito.mock(MongoCursor.class);
		FindIterable<Document> ite = Mockito.mock(FindIterable.class);
		MongoCollection<Document> col = Mockito.mock(MongoCollection.class);
		when(cursor.hasNext()).thenReturn(true).thenReturn(false);
		when(cursor.next()).thenReturn(document);
		when(ite.iterator()).thenReturn(cursor);
		when(col.find()).thenReturn(ite);
		List<MsgBean> msgbeans = mongosvc.selectMsg(col);
		msgbeans.forEach(s -> {
			assertThat(s.getFullmsg(), containsString("Test"));
			assertThat(s.getId(), is(1111));
		});
	}

	@Test
	@SuppressWarnings("unchecked")
	void testDeleteMsg() {
		MongoCollection<Document> col = Mockito.mock(MongoCollection.class);
		when(col.insertOne(ArgumentMatchers.any())).thenReturn(null);
		assertThat(mongosvc.deleteMsg(col), is("Delete Msg Collection"));
	}

	@Test
	void testGetCollection() {
		try {
			mongosvc.getCollection();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
