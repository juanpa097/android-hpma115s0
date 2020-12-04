package hpsaturn.pollutionreporter.reports.graph.data.services

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import hpsaturn.pollutionreporter.reports.shared.data.models.TracksData
import hpsaturn.pollutionreporter.reports.shared.domain.entities.SensorDataPoint
import hpsaturn.pollutionreporter.reports.shared.domain.entities.TracksDataNotFoundException
import hpsaturn.pollutionreporter.util.getSuspendValue
import javax.inject.Inject

class SensorReportDataFirebaseService @Inject constructor(
    private val database: DatabaseReference
) : SensorReportDataService {
    override suspend fun getSensorData(recordId: String): List<SensorDataPoint> {
        val result = database
            .child(TRACKS_DATA_COLLECTION)
            .child(recordId)
            .getSuspendValue()
            .getValue(object :
                GenericTypeIndicator<Map<String, @kotlin.jvm.JvmSuppressWildcards TracksData>>() {})

        return result?.values?.toList() ?: throw TracksDataNotFoundException()
    }

    companion object {
        private const val TRACKS_DATA_COLLECTION = "tracks_data"
    }
}
