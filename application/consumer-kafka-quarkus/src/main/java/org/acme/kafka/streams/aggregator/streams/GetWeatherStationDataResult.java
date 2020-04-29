package org.acme.kafka.streams.aggregator.streams;

import java.util.Optional;

import org.acme.kafka.streams.aggregator.model.WeatherStationData;

public class GetWeatherStationDataResult {

    private static GetWeatherStationDataResult NOT_FOUND =
            new GetWeatherStationDataResult(null);

    private final WeatherStationData result;

    private GetWeatherStationDataResult(WeatherStationData result) {
        this.result = result;
    }

    public static GetWeatherStationDataResult found(WeatherStationData data) {
        return new GetWeatherStationDataResult(data);
    }

    public static GetWeatherStationDataResult notFound() {
        return NOT_FOUND;
    }

    public Optional<WeatherStationData> getResult() {
        return Optional.ofNullable(result);
    }
}
