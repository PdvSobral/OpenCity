package com.example.opencity.storage

import android.app.Application
import android.content.Context

class OpenCityApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate();
        container = AppContainer(this)
    }
}

class AppContainer(context: Context) {
    val settingsRepository: AppSettingsInterface by lazy { AppSettingsRepository(context.applicationContext) }
    val languageRepository: LanguageRepositoryInterface by lazy { LanguageRepository() }
    
    /*
    val sessionRepository: SessionRepository = InMemorySessionRepository()   // signed-in state
    val reportRepository: ReportRepository = InMemoryReportRepository()     // replaces the literals
    val notificationRepository: NotificationRepository = InMemoryNotificationRepository()
    val markerRepository: MarkerRepository = InMemoryMarkerRepository()
    val serverAddressValidator: ServerAddressValidator = ServerAddressValidator()
    */
}
