package hpsaturn.pollutionreporter.reports.graph.presentation

import hpsaturn.pollutionreporter.core.domain.entities.ErrorResult
import hpsaturn.pollutionreporter.core.domain.entities.InProgress
import hpsaturn.pollutionreporter.core.domain.entities.Success
import hpsaturn.pollutionreporter.data.TestData
import hpsaturn.pollutionreporter.reports.graph.domain.usecases.LoadSensorReportData
import hpsaturn.pollutionreporter.util.InstantExecutorExtension
import hpsaturn.pollutionreporter.util.getValueForTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions

@Extensions(ExtendWith(MockKExtension::class), ExtendWith(InstantExecutorExtension::class))
internal class SensorReportDataGraphViewModelTest {

    private lateinit var sensorReportDataGraphViewModel: SensorReportDataGraphViewModel

    @MockK
    private lateinit var mockLoadSensorReportData: LoadSensorReportData

    private val tException = Exception()

    @BeforeEach
    fun setUp() {
        sensorReportDataGraphViewModel = SensorReportDataGraphViewModel(mockLoadSensorReportData)
    }

    @Test
    fun `should emit Success with the loaded data when sensorId is set after InProgress`() {
        // arrange
        val recordId = TestData.sensorReportInformation1.name
        coEvery { mockLoadSensorReportData(recordId) } returns Success(TestData.sensorDataPointList)
        sensorReportDataGraphViewModel.reportId.value = recordId
        // act
        val result = sensorReportDataGraphViewModel.reportData.getValueForTest(1)
        // assert
        assertEquals(Success(TestData.sensorDataPointList), result)
        coVerify { mockLoadSensorReportData(recordId) }
    }

    @Test
    fun `should emit InProgress before anything else when sensorId is set`() {
        // arrange
        val recordId = TestData.sensorReportInformation1.name
        coEvery { mockLoadSensorReportData(recordId) } returns Success(TestData.sensorDataPointList)
        sensorReportDataGraphViewModel.reportId.value = recordId
        // act
        val result = sensorReportDataGraphViewModel.reportData.getValueForTest()
        // assert
        assertEquals(InProgress, result)
    }

    @Test
    fun `should emit ErrorResult if LoadPublicSensorReports returns ErrorResult after InProgress`() {
        // arrange
        val recordId = TestData.sensorReportInformation1.name
        coEvery { mockLoadSensorReportData(recordId) } returns ErrorResult(tException)
        sensorReportDataGraphViewModel.reportId.value = recordId
        // act
        val result = sensorReportDataGraphViewModel.reportData.getValueForTest(1)
        // assert
        assertEquals(ErrorResult(tException), result)
        coVerify { mockLoadSensorReportData(recordId) }
    }

}