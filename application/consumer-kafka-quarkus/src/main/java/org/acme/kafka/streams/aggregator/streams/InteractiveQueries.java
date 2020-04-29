package org.acme.kafka.streams.aggregator.streams;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.kafka.streams.aggregator.model.Aggregation;
import org.acme.kafka.streams.aggregator.model.WeatherStationData;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

@ApplicationScoped
public class InteractiveQueries {

    @Inject
    KafkaStreams streams;

    public GetWeatherStationDataResult getWeatherStationData(int id) {
        Aggregation result = getWeatherStationStore().get(id);

        if (result != null) {
            return GetWeatherStationDataResult.found(WeatherStationData.from(result));
        }
        else {
            return GetWeatherStationDataResult.notFound();
        }
    }

    private ReadOnlyKeyValueStore<Integer, Aggregation> getWeatherStationStore() {
        while (true) {
            try {
                return streams.store(TopologyProducer.WEATHER_STATIONS_STORE, QueryableStoreTypes.keyValueStore());
            } catch (InvalidStateStoreException e) {
                // ignore, store not ready yet
            }
        }
    }
}
