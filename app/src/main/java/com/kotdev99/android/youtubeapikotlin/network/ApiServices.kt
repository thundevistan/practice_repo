package com.kotdev99.android.youtubeapikotlin.network

import com.kotdev99.android.youtubeapikotlin.model.ChannelModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
	@GET("channels")
	fun getChannel(
		@Query("part") part: String,
		@Query("id") id: String
	): Call<ChannelModel>
}