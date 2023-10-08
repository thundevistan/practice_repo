package com.kotdev99.android.youtubeapikotlin.activities

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.kotdev99.android.youtubeapikotlin.activities.viewmodel.PlaylistItemViewModel
import com.kotdev99.android.youtubeapikotlin.adapter.PlaylistItemAdapter
import com.kotdev99.android.youtubeapikotlin.databinding.ActivityPlaylistItemBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener

class PlaylistItemActivity : AppCompatActivity(), YouTubePlayerListener {

	private val binding by lazy { ActivityPlaylistItemBinding.inflate(layoutInflater) }
	private val viewModel: PlaylistItemViewModel by viewModels()
	private var youtubePlayer: YouTubePlayer? = null
	private val adapter by lazy { PlaylistItemAdapter() }

	private var isLoading = false
	private var isScroll = false
	private var currentItem = -1
	private var totalItem = -1
	private var scrollOutItem = -1
	private var isAllVideoLoaded = false
	private var isPlayingVideo = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		initView()
		initYouTubePlayerView()
	}

	private fun initView() {
		supportActionBar?.hide()

		val manager = LinearLayoutManager(this, HORIZONTAL, false)
		binding.rvPlaylist.adapter = adapter
		binding.rvPlaylist.layoutManager = manager
		adapter.addListener = PlaylistItemAdapter.ItemClickListener() { data ->
			data.contentDetail.videoId.let { id ->
				youtubePlayer?.loadVideo(id, 0f)
			}
			binding.tvVideoTitle.text = data.snippetYt.title
			binding.tvVideoDescription.text = data.snippetYt.description
			binding.tvVideoDescription.movementMethod = ScrollingMovementMethod()
		}

		// infinite scroll 구현
		binding.rvPlaylist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
				super.onScrollStateChanged(recyclerView, newState)

				if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
					isScroll = true
				}
			}

			override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
				super.onScrolled(recyclerView, dx, dy)

				currentItem = manager.childCount
				totalItem = manager.itemCount
				scrollOutItem = manager.findFirstVisibleItemPosition()
				if (isScroll && (currentItem + scrollOutItem == totalItem)) {
					isScroll = false
					if (!isLoading) {
						if (!isAllVideoLoaded) {
							val playlistId = intent.getStringExtra("playlist_id")
							playlistId?.let { viewModel.getPlaylistItem(it) }
						} else {
							Toast.makeText(
								this@PlaylistItemActivity,
								"All video loaded",
								Toast.LENGTH_SHORT
							)
								.show()
						}
					}
				}
			}
		})

		viewModel.isLoading.observe(this) {
			isLoading = it
			binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
		}

		viewModel.isAllItemLoaded.observe(this) {
			isAllVideoLoaded = it
			if (it) Toast.makeText(
				this,
				"All video has been loaded",
				Toast.LENGTH_SHORT
			).show()
		}
	}

	private fun initYouTubePlayerView() {
		lifecycle.addObserver(binding.youtubePlayerView)
		binding.youtubePlayerView.addYouTubePlayerListener(this)

		viewModel.playlistItem.observe(this) {
			it?.let { it1 -> adapter.setData(it1.items, binding.rvPlaylist) }
			if (!isPlayingVideo) {
				isPlayingVideo = true
				it?.items?.get(0)?.contentDetail?.videoId?.let { it1 ->
					youtubePlayer?.loadVideo(it1, 0f)
				}
			}
			binding.tvVideoTitle.text = it?.items?.get(0)?.snippetYt?.title
			binding.tvVideoDescription.text = it?.items?.get(0)?.snippetYt?.description
			binding.tvVideoDescription.movementMethod = ScrollingMovementMethod()
		}
	}

	override fun onApiChange(youTubePlayer: YouTubePlayer) {

	}

	override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {

	}

	override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {

	}

	override fun onPlaybackQualityChange(
		youTubePlayer: YouTubePlayer,
		playbackQuality: PlayerConstants.PlaybackQuality
	) {

	}

	override fun onPlaybackRateChange(
		youTubePlayer: YouTubePlayer,
		playbackRate: PlayerConstants.PlaybackRate
	) {

	}

	override fun onReady(youTubePlayer: YouTubePlayer) {
		youtubePlayer = youTubePlayer

		val playlistId = intent.getStringExtra("playlist_id")
		playlistId?.let {
			viewModel.getPlaylistItem(it)
		}
	}

	override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
		if (state == PlayerConstants.PlayerState.PLAYING) isPlayingVideo = true
	}

	override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {

	}

	override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
		isPlayingVideo = videoId.isNotEmpty()
	}

	override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {

	}
}