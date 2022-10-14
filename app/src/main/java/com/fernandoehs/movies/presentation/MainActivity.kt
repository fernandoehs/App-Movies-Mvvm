package com.fernandoehs.movies.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.fernandoehs.movies.utils.FragmentContainerHelper
import com.fernandoehs.movies.databinding.ActivityMainBinding
import com.fernandoehs.movies.presentation.detail.DetailFragment
import com.fernandoehs.movies.presentation.interfaces.IScreenNavigation
import com.fernandoehs.movies.presentation.movies.MoviesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IScreenNavigation.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mFContainerHelper: FragmentContainerHelper
    private var mainFragment: MoviesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initElements()
        showMoviesFragment()
    }

    private fun initElements(){
        mFContainerHelper = FragmentContainerHelper(this, binding.zicAttFrameContainer)
        setViewFitToHeightScreen()
    }

    private fun showMoviesFragment() {
        if (mainFragment == null) mainFragment = MoviesFragment.newInstance(this)
        pushFragment(mainFragment!!, true)
    }

    private fun pushFragment(
        fragment: Fragment,
        isReplacement: Boolean
    ) {
        mFContainerHelper.updateFragmentContainer(fragment, isReplacement)
    }


    private fun setViewFitToHeightScreen() {
        val window: Window = window
        val winParams: WindowManager.LayoutParams = window.attributes
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        window.attributes = winParams
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    override fun navigateToMoviesDetail(
        title: String,
        launchDate: String,
        language: String,
        rate: String,
        overview: String,
        posterPath: String,
        movieId: String,
    ) {
        val fragment = DetailFragment.newInstance(
            title,
            launchDate,
            language,
            rate,
            overview,
            posterPath,
            movieId,
            this
        )
        pushFragment(fragment, false)
    }

    override fun onBackGeneral() {
        super.onBackPressed()
    }

}