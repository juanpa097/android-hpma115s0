package hpsaturn.pollutionreporter.reports

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import hpsaturn.pollutionreporter.core.data.mappers.Mapper
import hpsaturn.pollutionreporter.reports.graph.data.repositories.SensorReportDataRepositoryImpl
import hpsaturn.pollutionreporter.reports.graph.data.services.SensorReportDataFirebaseService
import hpsaturn.pollutionreporter.reports.graph.data.services.SensorReportDataService
import hpsaturn.pollutionreporter.reports.graph.domain.repositories.SensorReportDataRepository
import hpsaturn.pollutionreporter.reports.open.data.mappers.SensorReportInformationMapper
import hpsaturn.pollutionreporter.reports.open.data.models.TracksInfo
import hpsaturn.pollutionreporter.reports.open.data.repositories.OpenSensorReportsRepositoryImpl
import hpsaturn.pollutionreporter.reports.open.data.services.SensorReportsFirebaseService
import hpsaturn.pollutionreporter.reports.open.data.services.SensorReportsService
import hpsaturn.pollutionreporter.reports.open.domain.repositories.OpenSensorReportsRepository
import hpsaturn.pollutionreporter.reports.shared.data.mappers.SensorDataPointMapper
import hpsaturn.pollutionreporter.reports.shared.data.models.TracksData
import hpsaturn.pollutionreporter.reports.shared.domain.entities.SensorDataPoint
import hpsaturn.pollutionreporter.reports.shared.domain.entities.SensorReportInformation

@Module
@InstallIn(ApplicationComponent::class)
abstract class SensorReportModule {

    @Binds
    abstract fun bindSensorDataPointMapper(
        sensorDataPointMapper: SensorDataPointMapper
    ): Mapper<TracksData, SensorDataPoint>

    @Binds
    abstract fun bindSensorReportInformationMapper(
        sensorDataPointMapper: SensorReportInformationMapper
    ): Mapper<TracksInfo, SensorReportInformation>

    @Binds
    abstract fun bindOpenSensorReportsRepositoryImpl(
        sensorReportRepositoryImpl: OpenSensorReportsRepositoryImpl
    ): OpenSensorReportsRepository

    @Binds
    abstract fun bindSensorReportRepositoryImpl(
        sensorReportDataRepositoryImpl: SensorReportDataRepositoryImpl
    ): SensorReportDataRepository

    @Binds
    abstract fun bindPublicSensorReportFirebaseService(
        publicSensorReportFirebaseService: SensorReportsFirebaseService
    ): SensorReportsService

    @Binds
    abstract fun bindSensorReportDataFirebaseService(
        sensorReportDataFirebaseService: SensorReportDataFirebaseService
    ): SensorReportDataService
}