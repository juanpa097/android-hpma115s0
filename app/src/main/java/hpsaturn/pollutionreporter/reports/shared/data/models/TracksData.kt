package hpsaturn.pollutionreporter.reports.shared.data.models

/**
 * Information of single data point of a report.
 * @property alt altitude of sea level of the data point.
 * @property P10 value of the P10 contaminant.
 * @property P25 value of the P2.5 contaminant.
 * @property spd value of SPD.
 * @property lat Latitude of the data point.
 * @property lon Longitude of the data point.
 * @property timestamp Timestamp of the data point.
 */

class TracksData(
    val alt: Double = 0.0,
    val P10: Double = 0.0,
    val P25: Double = 0.0,
    val spd: Double = 0.0,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val timestamp: Long = 0
)
