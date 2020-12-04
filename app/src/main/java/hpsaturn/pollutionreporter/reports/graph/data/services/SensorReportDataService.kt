package hpsaturn.pollutionreporter.reports.graph.data.services

import hpsaturn.pollutionreporter.reports.shared.domain.entities.SensorDataPoint

interface SensorReportDataService {
    suspend fun getSensorData(recordId: String): List<SensorDataPoint>
}