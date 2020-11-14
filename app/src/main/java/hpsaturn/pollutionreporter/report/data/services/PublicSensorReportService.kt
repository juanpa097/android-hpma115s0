package hpsaturn.pollutionreporter.report.data.services

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import hpsaturn.pollutionreporter.report.data.models.TracksInfo
import hpsaturn.pollutionreporter.report.domain.entities.TracksInfoNotFoundException
import hpsaturn.pollutionreporter.util.getSuspendValue
import javax.inject.Inject

interface PublicSensorReportService {
    suspend fun getTracksInfo(): List<TracksInfo>
}

class PublicSensorReportServiceImp @Inject constructor(
    private val database: DatabaseReference
) : PublicSensorReportService {

    override suspend fun getTracksInfo(): List<TracksInfo> {
        val result = database
            .child(TRACKS_INFO_COLLECTION)
            .getSuspendValue()
            .getValue(object : GenericTypeIndicator<List<@JvmSuppressWildcards TracksInfo>>() {})
        return result ?: throw TracksInfoNotFoundException()
    }

    companion object {
        private const val TRACKS_INFO_COLLECTION = "tracks_info"
    }

}