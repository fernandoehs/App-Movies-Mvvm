package com.fernandoehs.movies.presentation.movies

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fernandoehs.movies.utils.MoviesType
import com.fernandoehs.movies.core.extensions.gone
import com.fernandoehs.movies.core.extensions.show
import com.fernandoehs.movies.utils.Constants
import com.fernandoehs.movies.utils.Utils
import com.fernandoehs.movies.databinding.MovieBigItemBinding
import com.fernandoehs.movies.databinding.MovieItemBinding
import com.fernandoehs.movies.domain.model.Movie
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class MoviesAdapter(private val onClick: (Movie) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var items = mutableListOf<Movie>()
    private lateinit var bindingBigMovie: MovieBigItemBinding
    private lateinit var bindingNormalMovie: MovieItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType){
            MoviesType.NORMAL_MOVIE.value -> {
                bindingNormalMovie = MovieItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )

                MoviesViewHolder(bindingNormalMovie)
            }

            MoviesType.BIG_MOVIE.value -> {
                bindingBigMovie = MovieBigItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )

                MoviesBigViewHolder(bindingBigMovie)
            }

            else ->{
                bindingNormalMovie = MovieItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )

                MoviesViewHolder(bindingNormalMovie)
            }
        }

    override fun getItemViewType(position: Int): Int =
        when(items[position].posterBig){
            false -> MoviesType.NORMAL_MOVIE.value
            true -> MoviesType.BIG_MOVIE.value
        }

    override fun getItemCount(): Int = items.size

    fun addItem(newMovie: Movie){
        items.add(newMovie)
        notifyItemInserted(items.size - 1)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        when(items[position].posterBig){
            false -> (holder as MoviesViewHolder).onBind(items[position], onClick)
            true -> (holder as MoviesBigViewHolder).onBind(items[position], onClick)
        }
    }

    fun clear(){
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }
}

class MoviesViewHolder(private val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root){
    private val mContext = binding.root.context
    private val image = binding.imgBanner
    fun onBind(
        item: Movie,
        onClick: (Movie) -> Unit
    ){

        val urlImage = Constants.POSTER_URL_BASE + item.poster_path
        if(Utils.isConnected(mContext)){

            Picasso.get().load(urlImage).into(object : Target{
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    binding.movieItemSpinner.gone()
                    image.show()
                    Utils.saveImageToStorage(bitmap, item.id.toString(), binding.root.context)
                    image.setImageBitmap(bitmap)
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    image.gone()
                    binding.movieItemSpinner.show()
                }

            })
        } else {
            binding.movieItemSpinner.gone()
            Utils.showPosterFromDevice(
                item.id.toString(),
                image,
                mContext
            )
        }

        image.setOnClickListener {
            onClick(item)
        }
    }
}

class MoviesBigViewHolder(private val binding: MovieBigItemBinding): RecyclerView.ViewHolder(binding.root){
    private val mContext = binding.root.context
    private val image = binding.imgBannerBig
    fun onBind(
        item: Movie,
        onClick: (Movie) -> Unit
    ){
        val urlImage = Constants.POSTER_URL_BASE + item.poster_path
        if(Utils.isConnected(mContext)) {
            Picasso.get().load(urlImage).into(object : Target{
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    binding.movieBigItemSpinner.gone()
                    image.show()
                    Utils.saveImageToStorage(bitmap, item.id.toString(), binding.root.context)
                    image.setImageBitmap(bitmap)
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    image.gone()
                    binding.movieBigItemSpinner.show()
                }

            })

        }else {
            binding.movieBigItemSpinner.gone()
            Utils.showPosterFromDevice(
                item.id.toString(),
                image,
                mContext
            )
        }

        image.setOnClickListener {
            onClick(item)
        }
    }
}