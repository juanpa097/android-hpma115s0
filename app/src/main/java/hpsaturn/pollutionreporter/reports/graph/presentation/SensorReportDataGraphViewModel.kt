package hpsaturn.pollutionreporter.reports.graph.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import hpsaturn.pollutionreporter.core.domain.entities.InProgress
import hpsaturn.pollutionreporter.core.domain.entities.Result
import hpsaturn.pollutionreporter.reports.graph.domain.usecases.LoadSensorReportData
import hpsaturn.pollutionreporter.reports.shared.domain.entities.SensorDataPoint

class SensorReportDataGraphViewModel @ViewModelInject constructor(
    private val loadSensorReportData: LoadSensorReportData
) : ViewModel() {
    val reportId = MutableLiveData<String>()

    val reportData: LiveData<Result<List<SensorDataPoint>>> = reportId.switchMap {
        liveData {
            emit(InProgress)
            emit(loadSensorReportData(it))
        }
    }
}
