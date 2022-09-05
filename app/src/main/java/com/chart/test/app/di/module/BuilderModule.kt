package com.chart.test.app.di.module

import com.chart.test.app.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @ContributesAndroidInjector(modules = [LauncherActivityModule::class])
    internal abstract fun bindLauncherActivity(): MainActivity
}
