package com.alialfayed.pagination.kotlin.model

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.alialfayed.pagination.kotlin.R
import com.alialfayed.pagination.kotlin.utils.BASE_URL_IMG
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.gson.annotations.SerializedName

data class ResponseTopMovies(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

data class ResultsItem(

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("original_title")
	val originalTitle: String? = null,

	@field:SerializedName("video")
	val video: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("genre_ids")
	val genreIds: List<Int?>? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null
)

@BindingAdapter("app:imageMovie" , "app:progressMovie")
fun setImageMovie(image: ImageView, imageUrl: String? ,progressMovie : ProgressBar ) {
	val url = BASE_URL_IMG + imageUrl
	Glide.with(image.context)
		.load(url)
		.listener(object: RequestListener<Drawable> {
			override fun onLoadFailed(
				e: GlideException?,
				model: Any?,
				target: Target<Drawable>?,
				isFirstResource: Boolean
			): Boolean {
				progressMovie.visibility = View.GONE
				return false
			}

			override fun onResourceReady(
				resource: Drawable?,
				model: Any?,
				target: Target<Drawable>?,
				dataSource: DataSource?,
				isFirstResource: Boolean
			): Boolean {
				progressMovie.visibility = View.GONE
				return false
			}

		})
		.diskCacheStrategy(DiskCacheStrategy.ALL)
		.centerCrop()
		.into(image)

}

@BindingAdapter("app:yearMovie" ,  "app:originalLanguage")
fun setYearMovie(year : TextView,releaseDate: String ,originalLanguage : String ) {
	year.text = releaseDate.substring(0, 4) + " | " + originalLanguage.toUpperCase()
}
