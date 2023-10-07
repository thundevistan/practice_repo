package com.kotdev99.android.youtubeapikotlin.ui.video

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotdev99.android.youtubeapikotlin.R
import com.kotdev99.android.youtubeapikotlin.adapter.VideoAdapter
import com.kotdev99.android.youtubeapikotlin.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {

	private var _binding: FragmentVideoBinding? = null
	private val binding get() = _binding!!
	private val viewModel: VideoViewModel by viewModels()
	private val adapter = VideoAdapter()
	private val menuHost by lazy { requireActivity() }
	private lateinit var searchView: SearchView

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
		_binding = FragmentVideoBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initRecycler()
		initMenu()
	}

	// recycerView 구현
	private fun initRecycler() = with(binding) {
		val manager = LinearLayoutManager(requireActivity())

		rvVideo.adapter = adapter
		rvVideo.layoutManager = manager

		// infinite scroll 구현
		rvVideo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
							viewModel.getVideoList()
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

		viewModel.video.observe(viewLifecycleOwner) {
			if (it != null && it.items.isNotEmpty()) {
				adapter.setData(it.items, rvVideo)
			}
		}

		viewModel.isLoading.observe(viewLifecycleOwner) {
			isLoading = it
			binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
		}

		viewModel.isAllVideoLoaded.observe(viewLifecycleOwner) {
			isAllVideoLoaded = it
			if (it) Toast.makeText(
				requireActivity(),
				"All video has been loaded",
				Toast.LENGTH_SHORT
			).show()
		}
	}

	// menu inflate 및 검색 기능
	private fun initMenu() {
		val imm =
			requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

		menuHost.addMenuProvider(object : MenuProvider {
			override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
				menuInflater.inflate(R.menu.menu_search, menu)
				searchView = menu.findItem(R.id.menu_search).actionView as SearchView
				searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
					override fun onQueryTextSubmit(query: String?): Boolean {
						viewModel.querySearch = query
						viewModel.nextPageToken = null
						adapter.clearAll()
						viewModel.getVideoList()
						imm.hideSoftInputFromWindow(view?.windowToken, 0)
						return true
					}

					override fun onQueryTextChange(newText: String?): Boolean {
						return false
					}
				})
			}

			override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
				return false
			}
		}, viewLifecycleOwner)
	}
}