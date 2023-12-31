package com.kotdev99.android.youtubeapikotlin.ui.channel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotdev99.android.youtubeapikotlin.model.ChannelModel
import com.kotdev99.android.youtubeapikotlin.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChannelViewModel : ViewModel() {

	private val _channel = MutableLiveData<ChannelModel?>()
	val channel: LiveData<ChannelModel?>
		get() = _channel

	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean>
		get() = _isLoading

	private val _message = MutableLiveData<String>()
	val message: LiveData<String>
		get() = _message

	init {
		getChannel()
	}

	private fun getChannel() {
		_isLoading.value = true

		val client = ApiConfig.getService()
			.getChannel("snippet, brandingSettings", "UCmN88HSz-EQdwrkGXkc1ElA")
		client.enqueue(object : Callback<ChannelModel> {
			override fun onResponse(call: Call<ChannelModel>, response: Response<ChannelModel>) {
				_isLoading.value = false
				if (response.isSuccessful) {
					val data = response.body()
					if (data != null && data.items.isNotEmpty()) {
						_channel.value = data
					} else {
						_message.value = "No Chaneel"
					}
				} else {
					_message.value = response.message()
				}
			}

			override fun onFailure(call: Call<ChannelModel>, t: Throwable) {
				_isLoading.value = false
				Log.e(TAG, "Failed: ", t)
				_message.value = t.message
			}
		})
	}

	companion object {
		private val TAG = ChannelViewModel::class.java.simpleName
	}
}