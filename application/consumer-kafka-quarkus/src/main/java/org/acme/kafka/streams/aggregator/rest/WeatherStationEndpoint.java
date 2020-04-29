package org.acme.kafka.streams.aggregator.rest;

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

@ApplicationScoped
@Path("/weather-stations")
public class WeatherStationEndpoint {

    @Inject
    InteractiveQueries interactiveQueries;

    @GET
    @Path("/data/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWeatherStationData(@PathParam("id") int id) {
        GetWeatherStationDataResult result = interactiveQueries.getWeatherStationData(id);

        if (result.getResult().isPresent()) {
            return Response.ok(result.getResult().get()).build();
        }
        else {
            return Response.status(Status.NOT_FOUND.getStatusCode(),
                    "No data found for weather station " + id).build();
        }
    }
}
