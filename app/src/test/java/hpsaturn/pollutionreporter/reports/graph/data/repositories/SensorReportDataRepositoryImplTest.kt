package hpsaturn.pollutionreporter.reports.graph.data.repositories

import hpsaturn.pollutionreporter.core.domain.entities.ErrorResult
import hpsaturn.pollutionreporter.core.domain.entities.Success
import hpsaturn.pollutionreporter.data.TestData
import hpsaturn.pollutionreporter.reports.graph.data.services.SensorReportDataService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class SensorReportDataRepositoryImplTest {

    private lateinit var repository: SensorReportDataRepositoryImpl

    @MockK
    private lateinit var mockSensorReportDataService: SensorReportDataService

    @BeforeEach
    fun setUp() {
        repository = SensorReportDataRepositoryImpl(mockSensorReportDataService)
    }

    @Test
    fun `should return remote data when the call to remote data source is ok`() = runBlockingTest {
        // TODO (juanpa097) - Make return TestData.sensorReportInformation1.tracks when input is TestData.sensorReportInformation1.name
        // arrange
        coEvery { mockSensorReportDataService.getSensorData(TestData.sensorReportInformation1.name) } returns TestData.trackDataList
        // act
        val result = repository.getSensorReportData(TestData.sensorReportInformation1.name)
        // assert
        coVerify { mockSensorReportDataService.getSensorData(TestData.sensorReportInformation1.name) }
        assertEquals(Success(TestData.trackDataList), result)
    }

    @Test
    fun `should wrap the in ErrorResponse in case service throws an error`() = runBlockingTest {
        // arrange
        val tException = Exception()
        coEvery { mockSensorReportDataService.getSensorData(TestData.sensorReportInformation1.name) } throws tException
        // act
        val result = repository.getSensorReportData(TestData.sensorReportInformation1.name)
        // assert
        coVerify { mockSensorReportDataService.getSensorData(TestData.sensorReportInformation1.name) }
        assertEquals(ErrorResult(tException), result)
    }

}