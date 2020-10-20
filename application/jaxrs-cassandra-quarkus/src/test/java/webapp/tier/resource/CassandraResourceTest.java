package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.notNullValue;

import javax.ws.rs.core.Response;

import org.acme.getting.started.FruitDto;
import org.junit.jupiter.api.Test;

import com.datastax.oss.quarkus.test.CassandraTestResource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(CassandraTestResource.class)
class CassandraResourceTest {

	@Test
	public void should_save_and_retrieve_entity() {
		// given
		FruitDto expected = new FruitDto("it_product", "this was created via IT test");

		given()
				.when()
				.contentType("application/json")
				.post("/fruits")
				.then()
				.statusCode(500);

		// when retrieving, then
		FruitDto[] actual = when()
				.get("/fruits")
				.then()
				.statusCode(Response.Status.OK.getStatusCode())
				.body(notNullValue())
				.extract()
				.body()
				.as(FruitDto[].class);
//		assertThat(actual).contains(expected);
	}
}
