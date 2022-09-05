package com.chart.test.app.ui.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<V : BaseViewModel>(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    internal var subscription: CompositeDisposable = CompositeDisposable()
    private var mActivity: AppCompatActivity? = null
    private var mViewModel: V? = null

    abstract fun getViewModel(): V

    abstract fun bindViewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCompatActivity) {
            val activity = context as AppCompatActivity?
            mActivity = activity!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
    }

    override fun onResume() {
        super.onResume()
        bindViewModel()
    }

    override fun onPause() {
        super.onPause()
        unbindViewModel()
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    private fun unbindViewModel() {
        subscription.dispose()
    }
}
