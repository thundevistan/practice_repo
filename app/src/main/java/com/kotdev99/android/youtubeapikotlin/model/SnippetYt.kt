package com.kotdev99.android.youtubeapikotlin.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SnippetYt (
	@SerializedName("title")
	val title: String,

	@SerializedName("description")
	val description: String,

	@SerializedName("publishedAt")
	val publishedAt: String,

	@SerializedName("customUrl")
	val customUrl: String,

	@SerializedName("thumbnails")
	val thumbnail: ThumbnailsYt,

	@SerializedName("position")
	val country: Int?
)