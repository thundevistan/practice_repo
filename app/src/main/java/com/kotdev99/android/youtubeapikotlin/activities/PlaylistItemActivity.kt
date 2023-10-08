package com.kotdev99.android.youtubeapikotlin.activities

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kotdev99.android.youtubeapikotlin.activities.viewmodel.PlaylistItemViewModel
import com.kotdev99.android.youtubeapikotlin.databinding.ActivityPlaylistItemBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener

class PlaylistItemActivity : AppCompatActivity(), YouTubePlayerListener {

	private val binding by lazy { ActivityPlaylistItemBinding.inflate(layoutInflater) }
	private val viewModel: PlaylistItemViewModel by viewModels()
	private var youtubePlayer: YouTubePlayer? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		initView()
		initYouTubePlayerView()
	}

	private fun initView() {
		supportActionBar?.hide()
	}

	private fun initYouTubePlayerView() {
		lifecycle.addObserver(binding.youtubePlayerView)
		binding.youtubePlayerView.addYouTubePlayerListener(this)

		viewModel.playlistItem.observe(this) {
			it?.items?.get(0)?.contentDetail?.videoId?.let { video ->
				youtubePlayer?.loadVideo(video, 0f)
			}
			binding.tvVideoTitle.text = it?.items?.get(0)?.snippetYt?.title
			binding.tvVideoDescription.text = it?.items?.get(0)?.snippetYt?.description
			binding.tvVideoDescription.movementMethod = ScrollingMovementMethod()
		}
	}

	override fun onApiChange(youTubePlayer: YouTubePlayer) {
		// TODO("Not yet implemented")
	}

	override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
		// TODO("Not yet implemented")
	}

	override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
		// TODO("Not yet implemented")
	}

	override fun onPlaybackQualityChange(
		youTubePlayer: YouTubePlayer,
		playbackQuality: PlayerConstants.PlaybackQuality
	) {
		// TODO("Not yet implemented")
	}

	override fun onPlaybackRateChange(
		youTubePlayer: YouTubePlayer,
		playbackRate: PlayerConstants.PlaybackRate
	) {
		// TODO("Not yet implemented")
	}

	override fun onReady(youTubePlayer: YouTubePlayer) {
		youtubePlayer = youTubePlayer

		val playlistId = intent.getStringExtra("playlist_id")
		playlistId?.let {
			viewModel.getPlaylistItem(it)
		}
	}

	override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
		// TODO("Not yet implemented")
	}

	override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
		// TODO("Not yet implemented")
	}

	override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
		// TODO("Not yet implemented")
	}

	override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {
		// TODO("Not yet implemented")
	}

}