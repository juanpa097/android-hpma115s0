package hpsaturn.pollutionreporter.reports.shared.data.mappers

import hpsaturn.pollutionreporter.core.data.mappers.Mapper
import hpsaturn.pollutionreporter.reports.shared.data.models.TracksData
import hpsaturn.pollutionreporter.reports.shared.domain.entities.SensorDataPoint
import hpsaturn.pollutionreporter.util.fromUnixToDate
import javax.inject.Inject

class SensorDataPointMapper @Inject constructor() : Mapper<TracksData, SensorDataPoint> {
    override fun invoke(input: TracksData): SensorDataPoint = SensorDataPoint(
        input.P10,
        input.P25,
        input.spd,
        input.lat,
        input.lon,
        input.timestamp.fromUnixToDate()
    )
}
