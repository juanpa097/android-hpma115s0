package hpsaturn.pollutionreporter.reports.shared.data.models

import hpsaturn.pollutionreporter.reports.shared.domain.entities.SensorDataPoint
import hpsaturn.pollutionreporter.util.fromUnixToDate

/**
 * Information of single data point of a report.
 * @property altitude altitude of sea level of the data point.
 * @property p10 value of the P10 contaminant.
 * @property p25 value of the P2.5 contaminant.
 * @property spd value of SPD.
 * @property latitude Latitude of the data point.
 * @property longitude Longitude of the data point.
 * @property timestamp Timestamp of the data point.
 */

class TracksData(
    val id: String,
    val altitude: Double,
    override val p10: Double,
    override val p25: Double,
    override val spd: Double,
    override val latitude: Double,
    override val longitude: Double,
    val timestamp: Long
) : SensorDataPoint(
    id,
    p10,
    p25,
    spd,
    latitude,
    longitude,
    timestamp.fromUnixToDate()
)