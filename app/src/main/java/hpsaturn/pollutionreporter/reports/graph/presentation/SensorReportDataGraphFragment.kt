package hpsaturn.pollutionreporter.reports.graph.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import hpsaturn.pollutionreporter.R
import hpsaturn.pollutionreporter.core.domain.entities.ErrorResult
import hpsaturn.pollutionreporter.core.domain.entities.InProgress
import hpsaturn.pollutionreporter.core.domain.entities.Success
import hpsaturn.pollutionreporter.reports.shared.domain.entities.SensorDataPoint
import kotlinx.android.synthetic.main.fragment_sensor_report_data_graph.*
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "SensorReportDataGraphFr"

@AndroidEntryPoint
class SensorReportDataGraphFragment : Fragment() {

    private val args: SensorReportDataGraphFragmentArgs by navArgs()
    private val sensorReportDataGraphViewModel: SensorReportDataGraphViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sensor_report_data_graph, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadReportDataPoints()
        sensorReportDataGraphViewModel.reportId.value = args.reportId
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        topAppBar.setupWithNavController(findNavController(), appBarConfiguration)

    }

    private fun loadReportDataPoints() {
        sensorReportDataGraphViewModel.reportData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Success -> loadChart(it.data)
                is InProgress -> renderProgress()
                is ErrorResult -> renderError(it.exception)
            }
        })
    }

    private fun renderError(exception: Throwable) {
        errorMessage.visibility = View.VISIBLE
        errorMessage.text = exception.message
    }

    private fun renderProgress() {
        reportGraph.visibility = View.INVISIBLE
        loadingIndicator.visibility = View.VISIBLE
    }


    private fun loadChart(dataPoints: List<SensorDataPoint>) {
        loadingIndicator.visibility = View.INVISIBLE
        reportGraph.visibility = View.VISIBLE
        val graphDataSet =
            LineDataSet(dataPoints.map(dataPointToEntry), getString(R.string.label_pm25)).apply {
                color = R.color.colorPrimaryDarkWeb
                setDrawCircles(false)
                lineWidth = 2f
            }
        stingAndStyleGraph(LineData(graphDataSet))
    }

    private val dataPointToEntry = { sensorDataPoint: SensorDataPoint ->
        Entry(
            sensorDataPoint.timestamp.time.toFloat(),
            sensorDataPoint.P25.toFloat()
        )
    }

    private fun stingAndStyleGraph(lineData: LineData) {
        reportGraph.axisLeft.apply {
            setDrawGridLines(false)
            setDrawAxisLine(false)
        }

        reportGraph.xAxis.apply {
            valueFormatter = XAxisDateFormatter
            setDrawGridLines(false)
            setDrawAxisLine(false)
        }

        reportGraph.apply {
            data = lineData
            setDrawGridBackground(false)
            setPinchZoom(true)
            isDoubleTapToZoomEnabled = true
            setDrawBorders(true)
            isAutoScaleMinMaxEnabled = true
            description.isEnabled = false
            setNoDataText(getString(R.string.msg_chart_loading))
            animateX(1500)
            legend.form = Legend.LegendForm.LINE
            invalidate() // Chart is refreshed.
        }
    }

    private object XAxisDateFormatter : IAxisValueFormatter {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String =
            SimpleDateFormat("h:mm a", Locale.ENGLISH).format(Date(value.toLong()))

    }

}

