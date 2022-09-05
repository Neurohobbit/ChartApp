package com.chart.test.app.di.module

import com.chart.test.app.ui.chart.ChartFragment
import com.chart.test.app.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface LauncherActivityModule {

    @ContributesAndroidInjector
    fun provideSearchFragmentFactory(): SearchFragment

    @ContributesAndroidInjector
    fun provideChartFragmentFactory(): ChartFragment
}
