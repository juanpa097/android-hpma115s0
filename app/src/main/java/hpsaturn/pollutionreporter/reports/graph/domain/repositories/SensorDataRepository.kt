package hpsaturn.pollutionreporter.reports.graph.domain.repositories

import hpsaturn.pollutionreporter.core.domain.entities.Result
import hpsaturn.pollutionreporter.reports.shared.domain.entities.SensorDataPoint

interface SensorDataRepository {
    suspend fun getSensorData(sensorId: String): Result<List<SensorDataPoint>>
}