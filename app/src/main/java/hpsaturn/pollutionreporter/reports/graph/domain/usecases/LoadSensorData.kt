package hpsaturn.pollutionreporter.reports.graph.domain.usecases

import hpsaturn.pollutionreporter.di.DispatchersModule.IoDispatcher
import hpsaturn.pollutionreporter.reports.graph.domain.repositories.SensorDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Loads a list of data points of a sensor.
 */
class LoadSensorData @Inject constructor(
    private val sensorDataRepository: SensorDataRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(sensorId: String) =
        withContext(ioDispatcher) { sensorDataRepository.getSensorData(sensorId) }
}