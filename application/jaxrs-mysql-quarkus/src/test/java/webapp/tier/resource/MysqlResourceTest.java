package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.MysqlService;

@QuarkusTest
class MysqlResourceTest {

	@Inject
	MysqlService svc;

	@BeforeEach
	public void createTable() {
		String createsql = "CREATE TABLE msg (id SERIAL PRIMARY KEY, msg TEXT NOT NULL)";
		try (Connection con = DriverManager
				.getConnection("jdbc:h2:tcp://localhost/mem:webapp;DB_CLOSE_DELAY=-1");
				Statement stmt = con.createStatement()) {
			stmt.executeUpdate(createsql);
		} catch (SQLException e) {
			e.fillInStackTrace();
		}
		return;
	}

	@AfterEach
	public void dropTable() {
		String createsql = "DROP TABLE msg";
		try (Connection con = DriverManager
				.getConnection("jdbc:h2:tcp://localhost/mem:webapp;DB_CLOSE_DELAY=-1");
				Statement stmt = con.createStatement()) {
			stmt.executeUpdate(createsql);
		} catch (SQLException e) {
			e.fillInStackTrace();
		}
		return;
	}

	@Test
	void testInsert() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/mysql/insert")
				.then()
				.statusCode(200)
				.body(containsString("Hello k8s-3tier-webapp with quarkus"));
	}

	@Test
	void testSelect() {
		svc.invalidateCache();
		given()
				.when()
				.get("/quarkus/mysql/select")
				.then()
				.statusCode(200)
				.body(containsString("No Data."));
	}

	@Test
	void testDelete() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/mysql/delete")
				.then()
				.statusCode(200)
				.body(containsString("Delete Msg Records"));
	}

}
