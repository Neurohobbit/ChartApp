package com.chart.test.app.ui.chart

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chart.test.app.R
import com.chart.test.app.databinding.FrChartBinding
import com.chart.test.app.ui.base.BaseFragment
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChartFragment : BaseFragment<ChartViewModel>(R.layout.fr_chart) {

    private val binding by viewBinding(FrChartBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var searchViewModel: ChartViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            with(lcChart) {
                setBackgroundColor(Color.WHITE)
                description.isEnabled = false
                setTouchEnabled(true)
                setDrawGridBackground(false)
                isDragEnabled = true
                setScaleEnabled(true)
                setPinchZoom(true)
            }
        }
    }

    override fun getViewModel(): ChartViewModel {
        searchViewModel = ViewModelProvider(this, viewModelFactory)[ChartViewModel::class.java]
        return searchViewModel
    }

    override fun bindViewModel() {
        subscription = CompositeDisposable()
        subscription.add(
            searchViewModel.pointsPairs
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val values = it.list.map { pair ->
                        Entry(pair.first.toFloat(), pair.second.toFloat())
                    }
                    val dataSets = mutableListOf<ILineDataSet>()
                    val lineDataSet = LineDataSet(values, "").apply {
                        setDrawIcons(false)
                        binding.lcChart.legend.isEnabled = false
                        enableDashedLine(10f, 5f, 0f)

                        color = Color.BLACK
                        setCircleColor(Color.BLACK)

                        lineWidth = 1f
                        circleRadius = 3f

                        setDrawCircleHole(false)

                        formLineWidth = 1f
                        formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
                        formSize = 15f

                        valueTextSize = 9f

                        enableDashedHighlightLine(10f, 5f, 0f)

                        setDrawFilled(true)
                        fillFormatter =
                            IFillFormatter { _, _ ->
                                binding.lcChart.axisLeft.axisMinimum
                            }
                    }
                    dataSets.add(lineDataSet)
                    val data = LineData(dataSets)
                    binding.lcChart.data = data

                    // Fix for lib bug
                    binding.lcChart.invalidate()
                }
        )
    }
}
