package hpsaturn.pollutionreporter.reports.graph.domain.usecases

import hpsaturn.pollutionreporter.core.domain.entities.Success
import hpsaturn.pollutionreporter.data.TestData
import hpsaturn.pollutionreporter.reports.graph.domain.repositories.SensorReportDataRepository
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
internal class LoadSensorReportDataTest {

    private lateinit var useCase: LoadSensorReportData

    @MockK
    private lateinit var mockSensorReportDataRepository: SensorReportDataRepository

    @JvmField
    @RegisterExtension
    val coroutineRule = MainCoroutineTestExtension()

    @BeforeEach
    fun setUp() {
        useCase = LoadSensorReportData(mockSensorReportDataRepository, coroutineRule.dispatcher)
    }

    @Test
    fun `should call the repository to fetch sensor's data`() = coroutineRule.runBlockingTest {
        // arrange
        coEvery {
            mockSensorReportDataRepository.getSensorReportData(TestData.sensorReportInformation1.name)
        } returns Success(TestData.sensorDataPointList)
        // act
        val result = useCase(TestData.sensorReportInformation1.name)
        // assert
        Assertions.assertEquals(Success(TestData.sensorDataPointList), result)
        coVerify { mockSensorReportDataRepository.getSensorReportData(TestData.sensorReportInformation1.name) }
    }

}