package com.fernandoehs.movies.presentation.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fernandoehs.movies.databinding.FragmentDetailBinding
import com.fernandoehs.movies.utils.Constants
import com.fernandoehs.movies.utils.Utils
import com.fernandoehs.movies.presentation.base.BaseFragment
import com.fernandoehs.movies.presentation.interfaces.IScreenNavigation
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


private const val ARG_TITLE = "param_title"
private const val ARG_LAUNCH_DATE = "param_launch_date"
private const val ARG_LANGUAGE = "param_language"
private const val ARG_RATE = "param_rate"
private const val ARG_OVERVIEW = "param_overview"
private const val ARG_POSTER_PATH = "param_poster_path"
private const val ARG_MOVIE_ID = "param_video_id"

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private var title: String? = null
    private var launchDate: String? = null
    private var language: String? = null
    private var rate: String? = null
    private var overview: String? = null
    private var posterPath: String? = null
    private var movieId: String? = null

    private lateinit var listener: IScreenNavigation.Listener
    private lateinit var detailViewModel: DetailViewModel


    companion object {
        @JvmStatic
        fun newInstance(
            title: String,
            launchDate: String,
            language: String,
            rate: String,
            overview: String,
            posterPath: String,
            movieId: String,
            listener: IScreenNavigation.Listener
        ) = DetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_LAUNCH_DATE, launchDate)
                putString(ARG_LANGUAGE, language)
                putString(ARG_RATE, rate)
                putString(ARG_OVERVIEW, overview)
                putString(ARG_POSTER_PATH, posterPath)
                putString(ARG_MOVIE_ID, movieId)
            }
            this.listener = listener
        }
    }

    override fun setupFragmentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun getViewContainer(): View = binding.root

    override fun initElements() {
        getMovieArguments()
        drawMovieDetail()
        initViewModel()
    }

    private fun initViewModel() {
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.trailerResultsModel.observe(viewLifecycleOwner) { trailerResults ->
            if(trailerResults.isNotEmpty()){
                val youtubeResults = trailerResults.filter { result ->
                    result.site.lowercase(Locale.getDefault()) == "youtube"
                }
                if (youtubeResults.isNotEmpty()) {
                    watchTrailer(youtubeResults.first().key)
                }else{
                    Utils.screenError(mContext){retry ->
                        if(retry){
                            detailViewModel.getTrailerResults(movieId!!)
                        }else{
                            listener.onBackGeneral()
                        }
                    }
                }
            }else{
                Utils.screenError(mContext){retry ->
                    if(retry){
                        detailViewModel.getTrailerResults(movieId!!)
                    }else{
                        listener.onBackGeneral()
                    }
                }
            }

            detailViewModel.showErrorModel.observe(viewLifecycleOwner){ screenError ->
                if(screenError){
                    Utils.screenError(mContext) { retry ->
                        if(retry){
                            detailViewModel.getTrailerResults(movieId!!)
                        }else{
                            listener.onBackGeneral()
                        }
                    }
                }
            }
        }
    }

    private fun drawMovieDetail() {
        if (Utils.isConnected(binding.root.context))
            Picasso.get().load(Constants.POSTER_URL_BASE + posterPath)
                .into(binding.detImgPosterMovie)
        else
            Utils.showPosterFromDevice(
                movieId.toString(),
                binding.detImgPosterMovie,
                binding.root.context
            )
        binding.detTvMovieTitle.text = title
        binding.detTvLaunchYear.text = launchDate
        binding.detTvLanguage.text = language
        binding.detTvRate.text = rate
        binding.detTvOverview.text = overview
        binding.detBtnVerTrailer.setOnClickListener {
            detailViewModel.getTrailerResults(movieId!!)
        }
    }

    private fun getMovieArguments() {
        arguments?.let {
            title = it.getString(ARG_TITLE)
            launchDate = it.getString(ARG_LAUNCH_DATE)
            language = it.getString(ARG_LANGUAGE)
            rate = it.getString(ARG_RATE)
            overview = it.getString(ARG_OVERVIEW)
            posterPath = it.getString(ARG_POSTER_PATH)
            movieId = it.getString(ARG_MOVIE_ID)
        }
    }

    private fun watchTrailer(youtubeVideoId: String){
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$youtubeVideoId"))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("${Constants.YOUTUBE_URL_BASE}$youtubeVideoId"))
        try {
            this.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            this.startActivity(intentBrowser)
        }
    }

}