package hpsaturn.pollutionreporter.reports.open.data.mappers

import hpsaturn.pollutionreporter.data.TestData
import hpsaturn.pollutionreporter.reports.shared.data.mappers.SensorDataPointMapper
import hpsaturn.pollutionreporter.util.fromUnixToDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SensorDataPointMapperTest {

    private lateinit var tSensorDataPointMapper: SensorDataPointMapper

    @BeforeEach
    fun setUp() {
        tSensorDataPointMapper = SensorDataPointMapper()
    }

    @Test
    fun `should map TrackData to SensorReportInformation`() {
        // act
        val response = tSensorDataPointMapper(TestData.trackData1)
        // assert
        assertEquals(TestData.trackData1.P10, response.P10)
        assertEquals(TestData.trackData1.P25, response.P25)
        assertEquals(TestData.trackData1.P25, response.P25)
        assertEquals(TestData.trackData1.spd, response.spd)
        assertEquals(TestData.trackData1.spd, response.spd)
        assertEquals(TestData.trackData1.lat, response.latitude)
        assertEquals(TestData.trackData1.lon, response.longitude)
        assertEquals(TestData.trackData1.timestamp.fromUnixToDate(), response.timestamp)
    }
}