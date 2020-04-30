package org.acme.kafka.streams.aggregator.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.kafka.streams.aggregator.model.Aggregation;
import org.acme.kafka.streams.aggregator.model.WeatherStationData;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.jboss.logging.Logger;

import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class InteractiveQueries {

	@Inject
	KafkaStreams streams;

	private static final Logger LOG = Logger.getLogger(InteractiveQueries.class);

	public GetWeatherStationDataResult getWeatherStationData(int id) {
		LOG.warn("getWeatherStationData: " + InteractiveQueries.class.getName());
		Aggregation result = getWeatherStationStore().get(id);

		if (result != null) {
			return GetWeatherStationDataResult.found(WeatherStationData.from(result));
		} else {
			return GetWeatherStationDataResult.notFound();
		}
	}

	public List<String> getStreamData() {
		LOG.warn("getStreamData: " + InteractiveQueries.class.getName());
		List<String> msglist = new ArrayList<>();
		ReadOnlyKeyValueStore<Integer, String> kvs = getStreamDataStore();
		KeyValueIterator<Integer, String> result = kvs.all();

		if (result != null) {
			while (result.hasNext()) {
		        KeyValue<Integer, String> kv = result.next();
				MsgBeanUtils msgbean = new MsgBeanUtils();
				msgbean.setId(kv.key);
				msgbean.setMessage(kv.value);
				msgbean.setFullmsgWithType(msgbean, "Get");
				LOG.info(msgbean.getFullmsg());
				msglist.add(msgbean.getFullmsg());
			}
			return msglist;
		} else {
			return null;
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

	private ReadOnlyKeyValueStore<Integer, String> getStreamDataStore() {
		while (true) {
			try {
				return streams.store(TopologyProducer.WEATHER_STATIONS_STORE, QueryableStoreTypes.keyValueStore());
			} catch (InvalidStateStoreException e) {
				// ignore, store not ready yet
			}
		}
	}

	public List<PipelineMetadata> getMetaData() {
		LOG.warn("getMetaData: " + InteractiveQueries.class.getName());
		return streams.allMetadataForStore(TopologyProducer.WEATHER_STATIONS_STORE)
				.stream()
				.map(m -> new PipelineMetadata(
						m.hostInfo().host() + ":" + m.hostInfo().port(),
						m.topicPartitions()
								.stream()
								.map(TopicPartition::toString)
								.collect(Collectors.toSet())))
				.collect(Collectors.toList());
	}
}
