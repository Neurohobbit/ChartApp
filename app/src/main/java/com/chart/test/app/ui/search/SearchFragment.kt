package com.chart.test.app.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chart.test.app.R
import com.chart.test.app.databinding.FrSearchBinding
import com.chart.test.app.ui.base.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchFragment : BaseFragment<SearchViewModel>(R.layout.fr_search) {

    private val binding by viewBinding(FrSearchBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var searchViewModel: SearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            etCount.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getPoints()
                }
                return@setOnEditorActionListener false
            }

            bStart.setOnClickListener {
                getPoints()
            }
        }
    }

    override fun getViewModel(): SearchViewModel {
        searchViewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        return searchViewModel
    }

    override fun bindViewModel() {
        subscription = CompositeDisposable()
        subscription.add(
            searchViewModel.resultSuccess
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    findNavController()
                        .navigate(SearchFragmentDirections.actionSearchFragmentToChartFragment())
                }
        )

        subscription.add(
            searchViewModel.resultError
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT)
                        .show()
                }
        )

        subscription.add(
            searchViewModel.requestInProgress
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    with(binding) {
                        etCount.isEnabled = !it
                        bStart.isEnabled = !it
                    }
                }
        )
    }

    private fun getPoints() {
        if (binding.etCount.text.isNotEmpty()) {
            searchViewModel.getPoints(binding.etCount.text.toString().toInt())
        }
    }
}
