package com.kotdev99.android.youtubeapikotlin.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotdev99.android.youtubeapikotlin.adapter.PlaylistAdapter
import com.kotdev99.android.youtubeapikotlin.databinding.FragmentPlaylistBinding

class PlaylistFragment : Fragment() {

	private var _binding: FragmentPlaylistBinding? = null
	private val binding get() = _binding!!
	private val viewModel: PlaylistViewModel by viewModels()
	private val adapter = PlaylistAdapter()

	private var isLoading = false
	private var isScroll = false
	private var currentItem = -1
	private var totalItem = -1
	private var scrollOutItem = -1
	private var isAllVideoLoaded = false

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentPlaylistBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView()
	}

	private fun initView() {
		val manager = LinearLayoutManager(requireActivity())

		binding.rvPlaylist.adapter = adapter
		binding.rvPlaylist.layoutManager = manager

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
							viewModel.getPlaylist()
						} else {
							Toast.makeText(
								requireActivity(),
								"All video loaded",
								Toast.LENGTH_SHORT
							)
								.show()
						}
					}
				}
			}
		})


		viewModel.playlist.observe(viewLifecycleOwner) {
			adapter.setData(it!!.items, binding.rvPlaylist)
		}

		viewModel.isLoading.observe(viewLifecycleOwner) {
			isLoading = it
			binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
		}

		viewModel.isAllPlaylistLoaded.observe(viewLifecycleOwner) {
			isAllVideoLoaded = it
			if (it) Toast.makeText(
				requireActivity(),
				"All video has been loaded",
				Toast.LENGTH_SHORT
			).show()
		}
	}
}