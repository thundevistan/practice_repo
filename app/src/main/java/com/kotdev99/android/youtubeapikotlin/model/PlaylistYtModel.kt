package com.kotdev99.android.youtubeapikotlin.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PlaylistYtModel(
	@SerializedName("nextPageToken")
	val nextPageToken: String,

	@SerializedName("items")
	val items: List<PlayListItem>
) {
	data class PlayListItem (
		@SerializedName("id")
		val id: String?,

		@SerializedName("snippet")
		val snippetYt: SnippetYt,

		@SerializedName("contentDetails")
		val contentDetail: ContentDetail
	) {
		data class ContentDetail (
			@SerializedName("itemCount")
			val itemCount: Int?,

			@SerializedName("videoId")
			val videoId: String
		)
	}
}
