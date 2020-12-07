package hpsaturn.pollutionreporter.reports.shared.domain.entities

import java.util.*

/**
 * Information of single data point of a report.
 * @property P10 value of the P10 contaminant.
 * @property P25 value of the P2.5 contaminant.
 * @property spd value of SPD.
 * @property latitude Latitude of the data point.
 * @property longitude Longitude of the data point.
 * @property timestamp Timestamp of the data point.
 */

open class SensorDataPoint(
    val P10: Double,
    val P25: Double,
    val spd: Double,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Date
)
