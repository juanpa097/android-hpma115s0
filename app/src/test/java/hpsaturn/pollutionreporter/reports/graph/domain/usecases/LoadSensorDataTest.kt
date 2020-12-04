package hpsaturn.pollutionreporter.reports.graph.domain.usecases

import hpsaturn.pollutionreporter.core.domain.entities.Success
import hpsaturn.pollutionreporter.data.TestData
import hpsaturn.pollutionreporter.reports.graph.domain.repositories.SensorDataRepository
import hpsaturn.pollutionreporter.util.InstantExecutorExtension
import hpsaturn.pollutionreporter.util.MainCoroutineTestExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
@Extensions(ExtendWith(MockKExtension::class), ExtendWith(InstantExecutorExtension::class))
internal class LoadSensorDataTest {

    private lateinit var useCase: LoadSensorData

    @MockK
    private lateinit var mockSensorDataRepository: SensorDataRepository

    @JvmField
    @RegisterExtension
    val coroutineRule = MainCoroutineTestExtension()

    @BeforeEach
    fun setUp() {
        useCase = LoadSensorData(mockSensorDataRepository, coroutineRule.dispatcher)
    }

    @Test
    fun `should call the repository to fetch sensor's data`() = coroutineRule.runBlockingTest {
        // arrange
        coEvery {
            mockSensorDataRepository.getSensorData(TestData.sensorReportInformation1.deviceId)
        } returns Success(TestData.sensorDataPointList)
        // act
        val result = useCase(TestData.sensorReportInformation1.deviceId)
        // assert
        Assertions.assertEquals(Success(TestData.sensorDataPointList), result)
        coVerify { mockSensorDataRepository.getSensorData(TestData.sensorReportInformation1.deviceId) }
    }

}