package com.fernandoehs.movies.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<Binding: ViewBinding>: Fragment() {

    protected var _binding: Binding? = null
    private lateinit var mView: View
    private lateinit var mContext: Context
    protected val  binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = this.setupFragmentView(inflater, container, savedInstanceState)
        mContext = mView.context
        initElements()
        return mView
    }


    abstract fun setupFragmentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View


    protected abstract fun getViewContainer(): View?


    protected abstract fun initElements()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}