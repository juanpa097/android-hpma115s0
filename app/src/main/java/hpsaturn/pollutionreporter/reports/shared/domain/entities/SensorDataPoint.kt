package hpsaturn.pollutionreporter.reports.shared.domain.entities

import java.util.*

/**
 * Information of single data point of a report.
 * @property pointId ID of the data point.
 * @property p10 value of the P10 contaminant.
 * @property p25 value of the P2.5 contaminant.
 * @property spd value of SPD.
 * @property latitude Latitude of the data point.
 * @property longitude Longitude of the data point.
 * @property datetime Timestamp of the data point.
 */

open class SensorDataPoint(
    val pointId: String,
    open val p10: Double,
    open val p25: Double,
    open val spd: Double,
    open val latitude: Double,
    open val longitude: Double,
    val datetime: Date
)