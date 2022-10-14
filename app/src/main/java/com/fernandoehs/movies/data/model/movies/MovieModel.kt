package com.fernandoehs.movies.data.model.movies

import com.google.gson.annotations.SerializedName

data class MovieModel (
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("backdrop_path") var backdrop_path: String,
    @SerializedName("id") var id: Long,
    @SerializedName("original_language") var original_language: String,
    @SerializedName("original_title") var original_title: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("popularity") var popularity: Double,
    @SerializedName("poster_path") var poster_path: String,
    @SerializedName("release_date") var release_year: String,
    @SerializedName("title") var title: String,
    @SerializedName("video") var video: Boolean,
    @SerializedName("vote_average") var vote_average: Double,
    @SerializedName("vote_count") var vote_count: Long,
    @SerializedName("posterBig") var posterBig: Boolean = false,
    @SerializedName("type") var type: String
){
    private fun getJustYearAndReplaceField(){
        if(!this.release_year.isNullOrEmpty()) {
            val array = this.release_year.split("-")
            this.release_year = array[0]
        }
    }

    fun setTopRatedType(){
        getJustYearAndReplaceField()
        this.type = "1"
    }

    fun setUpcomingType(){
        getJustYearAndReplaceField()
        this.type = "2"
    }
}