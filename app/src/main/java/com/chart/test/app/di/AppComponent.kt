package com.chart.test.app.di

import com.chart.test.app.App
import com.chart.test.app.di.module.AppContextModule
import com.chart.test.app.di.module.BuilderModule
import com.chart.test.app.di.module.NetworkModule
import com.chart.test.app.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        AppContextModule::class,
        BuilderModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
