package com.fernandoehs.movies.presentation.movies

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.fernandoehs.movies.R
import com.fernandoehs.movies.databinding.FragmentMoviesBinding
import com.fernandoehs.movies.utils.Utils
import com.fernandoehs.movies.domain.model.Movie
import com.fernandoehs.movies.presentation.base.BaseFragment
import com.fernandoehs.movies.presentation.interfaces.IScreenNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {

    private lateinit var moviesViewModel: MoviesViewModel
    private var moviesUpcomingAdapter: MoviesAdapter? = null
    private var moviesTopRatedAdapter: MoviesAdapter? = null
    private var moviesRecommendedAdapter: MoviesAdapter? = null
    private var recyclerUpcoming: RecyclerView? = null
    private var recyclerTopRated: RecyclerView? = null
    private var recyclerRecommended: RecyclerView? = null

    private var launchYear = ""
    private var language = ""
    private var launchYearSelected = false
    private var languageSelected = false

    private lateinit var listener: IScreenNavigation.Listener

    companion object {

        @JvmStatic
        fun newInstance(listener: IScreenNavigation.Listener): MoviesFragment = MoviesFragment().apply {
            this.listener = listener
        }
    }

    override fun setupFragmentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun getViewContainer(): View = binding.root

    override fun initElements() {
        initRecyclersAndAdapters()
        initViewModels()
        setListeners()
    }

    private fun initViewModels() {
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        moviesViewModel.onCreate()

        moviesViewModel.upcomingMoviesModel.observe(viewLifecycleOwner) { upcomingMovies ->
            setUpcomingMoviesAdapter(upcomingMovies)
        }


        moviesViewModel.topRatedMoviesModel.observe(viewLifecycleOwner) { topRatedMovies ->
            setTopRatedMoviesAdapter(topRatedMovies)
            moviesViewModel.getRecommendedMovies("%","%")
        }

        moviesViewModel.recommendedMoviesModel.observe(viewLifecycleOwner) { recommendedMovies ->
            setRecommendedMoviesAdapter(recommendedMovies)
        }
    }

    private fun initRecyclersAndAdapters() {
        moviesUpcomingAdapter = MoviesAdapter(){ movie ->
            listener.navigateToMoviesDetail(
                movie.title,
                movie.release_year,
                movie.original_language,
                movie.vote_average.toString(),
                movie.overview,
                movie.poster_path,
                movie.id.toString()
            )
        }
        moviesTopRatedAdapter = MoviesAdapter(){ movie ->
            listener.navigateToMoviesDetail(
                movie.title,
                movie.release_year,
                movie.original_language,
                movie.vote_average.toString(),
                movie.overview,
                movie.poster_path,
                movie.id.toString()
            )
        }
        moviesRecommendedAdapter = MoviesAdapter(){ movie ->
            listener.navigateToMoviesDetail(
                movie.title,
                movie.release_year,
                movie.original_language,
                movie.vote_average.toString(),
                movie.overview,
                movie.poster_path,
                movie.id.toString()
            )
        }

        recyclerUpcoming = binding.rvUpcomingMovies
        recyclerTopRated = binding.rvTopRatedMovies
        recyclerRecommended = binding.rvRecommendedMovies

        recyclerUpcoming?.adapter = moviesUpcomingAdapter
        recyclerUpcoming?.isNestedScrollingEnabled = false

        recyclerTopRated?.adapter = moviesTopRatedAdapter
        recyclerTopRated?.isNestedScrollingEnabled = false

        recyclerRecommended?.adapter = moviesRecommendedAdapter
        recyclerRecommended?.isNestedScrollingEnabled = false
    }

    private fun setUpcomingMoviesAdapter(upcomingMovies: List<Movie>){
        upcomingMovies.forEach{ upcomingMovie ->
            moviesUpcomingAdapter?.addItem(upcomingMovie)
        }

        recyclerUpcoming?.adapter = moviesUpcomingAdapter
        recyclerUpcoming?.adapter?.notifyDataSetChanged()
    }

    private fun setTopRatedMoviesAdapter(topRatedMovies: List<Movie>){
        topRatedMovies.forEach{ topRatedMovie ->
            moviesTopRatedAdapter?.addItem(topRatedMovie)
        }

        recyclerTopRated?.adapter = moviesTopRatedAdapter
        recyclerTopRated?.adapter?.notifyDataSetChanged()
    }

    private fun setRecommendedMoviesAdapter(recommendedMovies: List<Movie>){
        recommendedMovies.forEach{ recommendedMovie ->
            recommendedMovie.posterBig = true
            moviesRecommendedAdapter?.addItem(recommendedMovie)
        }

        recyclerRecommended?.adapter = moviesRecommendedAdapter
        recyclerRecommended?.adapter?.notifyDataSetChanged()
    }

    private fun setListeners(){
        binding.btnLaunchYear.setOnClickListener {
            launchYearSelected = !launchYearSelected
            setButtonStatus()
            moviesRecommendedAdapter?.clear()
            moviesViewModel.getRecommendedMovies("%$language","%$launchYear")
        }

        binding.btnLanguage.setOnClickListener {
            languageSelected = !languageSelected
            setButtonStatus()
            moviesRecommendedAdapter?.clear()
            moviesViewModel.getRecommendedMovies("%$language","%$launchYear")
        }
    }

    private fun setButtonStatus() {
        launchYear = if (launchYearSelected) {
            binding.btnLaunchYear.setBackgroundColor(context?.getColor(R.color.white) ?: 0)
            binding.btnLaunchYear.setTextColor(context?.getColor(R.color.black) ?: 0)
            "2020"
        }else {
            binding.btnLaunchYear.setBackgroundColor(context?.getColor(R.color.black) ?: 0)
            binding.btnLaunchYear.setTextColor(context?.getColor(R.color.white) ?: 0)
            ""
        }
        language = if (languageSelected) {
            binding.btnLanguage.setBackgroundColor(context?.getColor(R.color.white) ?: 0)
            binding.btnLanguage.setTextColor(context?.getColor(R.color.black) ?: 0)
            "es"
        }else {
            binding.btnLanguage.setBackgroundColor(context?.getColor(R.color.black) ?: 0)
            binding.btnLanguage.setTextColor(context?.getColor(R.color.white) ?: 0)
            ""
        }
    }

}