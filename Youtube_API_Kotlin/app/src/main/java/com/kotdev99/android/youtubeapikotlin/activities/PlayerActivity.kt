package com.kotdev99.android.youtubeapikotlin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotdev99.android.youtubeapikotlin.databinding.ActivityPlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class PlayerActivity : AppCompatActivity() {

	private var _binding: ActivityPlayerBinding? = null
	private val binding get() = _binding!!
	private lateinit var youTubePlayerView: YouTubePlayerView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityPlayerBinding.inflate(layoutInflater)
		setContentView(binding.root)

		youTubePlayerView = binding.youtubePlayerView
		initYouTubePlayerView()
	}

	private fun initYouTubePlayerView() {
		val videoId = intent.getStringExtra("video_id")!!
		val videoTitle = intent.getStringExtra("video_title")
		val videoDescription = intent.getStringExtra("video_description")

		binding.tvVideoTitle.text = videoTitle
		binding.tvVideoDescription.text = videoDescription

		lifecycle.addObserver(youTubePlayerView)

		val iFramePlayerOptions = IFramePlayerOptions.Builder()
			.controls(1)
			.rel(0)
			.ivLoadPolicy(1)
			.ccLoadPolicy(1)
			.autoplay(0)
			.build()

		youTubePlayerView.initialize(
			object : AbstractYouTubePlayerListener() {
				override fun onReady(youTubePlayer: YouTubePlayer) {

					youTubePlayer.loadOrCueVideo(lifecycle, videoId, 0f)
				}
			}, iFramePlayerOptions
		)
	}

	override fun onDestroy() {
		super.onDestroy()
//		binding.youtubePlayer.release()     // lifecycle 를 등록 했다면 생략 가능
	}
}