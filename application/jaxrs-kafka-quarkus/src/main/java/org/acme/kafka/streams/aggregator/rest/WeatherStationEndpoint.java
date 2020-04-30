package org.acme.kafka.streams.aggregator.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.kafka.streams.aggregator.streams.GetWeatherStationDataResult;
import org.acme.kafka.streams.aggregator.streams.InteractiveQueries;
import org.acme.kafka.streams.aggregator.streams.PipelineMetadata;
import org.apache.kafka.streams.KeyValue;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.annotations.SseElementType;

@ApplicationScoped
@Path("/quarkus/kafka")
public class WeatherStationEndpoint {

	@Inject
	InteractiveQueries interactiveQueries;

	@Inject
	@Channel("message")
	KeyValue<Integer, String> message;

	@GET
	@Path("/data/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWeatherStationData(@PathParam("id") int id) {
		GetWeatherStationDataResult result = interactiveQueries.getWeatherStationData(id);

		if (result.getResult().isPresent()) {
			return Response.ok(result.getResult().get()).build();
		} else if (result.getHost().isPresent()) {
			URI otherUri = getOtherUri(result.getHost().get(), result.getPort().getAsInt(),
					id);
			return Response.seeOther(otherUri).build();
		} else {
			return Response.status(Status.NOT_FOUND.getStatusCode(),
					"No data found for weather station " + id).build();
		}
	}

	@GET
	@Path("/stream/json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStreamData() {
		List<String> result = interactiveQueries.getStreamData();
		return Response.ok(result).build();
	}

	@GET
	@Path("/stream/sse")
	@Produces(MediaType.SERVER_SENT_EVENTS) // denotes that server side events (SSE) will be produced
	@SseElementType("text/plain") // denotes that the contained data, within this SSE, is just regular text/plain data
	public KeyValue<Integer, String> stream() {
		return message;
	}

	@GET
	@Path("/meta-data")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PipelineMetadata> getMetaData() {
		return interactiveQueries.getMetaData();
	}

	private URI getOtherUri(String host, int port, int id) {
		try {
			return new URI("http://" + host + ":" + port + "/quarkus/kafka/data/" + id);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
