package com.fernandoehs.movies.presentation.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernandoehs.movies.utils.Utils
import com.fernandoehs.movies.data.model.trailer.TrailerResult
import com.fernandoehs.movies.domain.GetTrailerResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getTrailerResultsUseCase: GetTrailerResultsUseCase,
    @ApplicationContext val context: Context
    ): ViewModel() {

    val trailerResultsModel = MutableLiveData<List<TrailerResult>>()
    val showErrorModel = MutableLiveData<Boolean>()

    fun getTrailerResults(videoId: String) {
        if(Utils.isConnected(context)){
            showErrorModel.postValue(false)
            viewModelScope.launch {
                val results = getTrailerResultsUseCase(videoId)
                if (results.isNotEmpty()) {
                    trailerResultsModel.postValue(results)
                }
            }
        }else{
            showErrorModel.postValue(true)
        }

    }
}