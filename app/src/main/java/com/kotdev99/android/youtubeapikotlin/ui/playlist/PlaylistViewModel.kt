package com.kotdev99.android.youtubeapikotlin.ui.playlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotdev99.android.youtubeapikotlin.model.PlaylistYtModel
import com.kotdev99.android.youtubeapikotlin.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistViewModel : ViewModel() {

	private val _playlist = MutableLiveData<PlaylistYtModel?>()
	val playlist: LiveData<PlaylistYtModel?> get() = _playlist

	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> get() = _isLoading

	private val _isAllPlaylistLoaded = MutableLiveData<Boolean>()
	val isAllPlaylistLoaded: LiveData<Boolean> get() = _isAllPlaylistLoaded

	private var nextPageToken: String? = null

	init {
		getPlaylist()
	}

	fun getPlaylist() {
		_isLoading.value = true
		val client = ApiConfig
			.getService()
			.getPlaylist("snippet, contentDetails", "UCGp4UBwpTNegd_4nCpuBcow", "10", nextPageToken)
		client.enqueue(object : Callback<PlaylistYtModel> {
			override fun onResponse(
				call: Call<PlaylistYtModel>,
				response: Response<PlaylistYtModel>
			) {
				_isLoading.value = false
				if (response.isSuccessful) {
					val data = response.body()
					if (data != null) {
						if (data.nextPageToken != null) {
							nextPageToken = data.nextPageToken
						} else {
							_isAllPlaylistLoaded.value = true
						}
						if (data.items.isNotEmpty()) {
							_playlist.value = data
						}
					}
				}
			}

			override fun onFailure(call: Call<PlaylistYtModel>, t: Throwable) {
				_isLoading.value = false
				Log.e(TAG, "Failure: ", t)
			}

		})
	}

	companion object {
		private val TAG = PlaylistViewModel::class.java.simpleName
	}
}