package hpsaturn.pollutionreporter.reports.graph.domain.usecases

import hpsaturn.pollutionreporter.di.DispatchersModule.IoDispatcher
import hpsaturn.pollutionreporter.reports.graph.domain.repositories.SensorReportDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Loads a list of data points of a sensor.
 */
class LoadSensorReportData @Inject constructor(
    private val sensorReportDataRepository: SensorReportDataRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(recordId: String) =
        withContext(ioDispatcher) { sensorReportDataRepository.getSensorData(recordId) }
}