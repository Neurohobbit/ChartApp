package com.chart.test.app.di.module

import android.content.Context
import com.chart.test.app.App
import com.chart.test.app.data.Repository
import com.chart.test.app.data.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppContextModule {

    @Provides
    internal fun provideContext(app: App): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: RepositoryImpl): Repository {
        return appDataManager
    }
}
