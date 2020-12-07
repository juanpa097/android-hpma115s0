package hpsaturn.pollutionreporter.reports.graph.data.repositories

import hpsaturn.pollutionreporter.core.domain.entities.ErrorResult
import hpsaturn.pollutionreporter.core.domain.entities.Result
import hpsaturn.pollutionreporter.core.domain.entities.Success
import hpsaturn.pollutionreporter.reports.graph.data.services.SensorReportDataService
import hpsaturn.pollutionreporter.reports.graph.domain.repositories.SensorReportDataRepository
import hpsaturn.pollutionreporter.reports.shared.domain.entities.SensorDataPoint
import javax.inject.Inject

class SensorReportDataRepositoryImpl @Inject constructor(
    private val sensorReportDataService: SensorReportDataService
) : SensorReportDataRepository {
    override suspend fun getSensorReportData(recordId: String): Result<List<SensorDataPoint>> =
        runCatching {
            Success(sensorReportDataService.getSensorData(recordId))
        }.getOrElse {
            ErrorResult(it)
        }
}
