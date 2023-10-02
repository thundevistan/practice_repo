package com.kotdev99.android.youtubeapikotlin.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotdev99.android.youtubeapikotlin.adapter.VideoAdapter
import com.kotdev99.android.youtubeapikotlin.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {

	private var _binding: FragmentVideoBinding? = null
	private val binding get() = _binding!!
	private val viewModel: VideoViewModel by viewModels()
	private val adapter = VideoAdapter()

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

		recycler()
	}

	private fun recycler() = with(binding) {
		rvVideo.adapter = adapter
		rvVideo.layoutManager = LinearLayoutManager(requireContext())

		viewModel.video.observe(viewLifecycleOwner) {
			if (it != null && it.items.isNotEmpty()) {
				adapter.setData(it.items)
			}
		}
	}
}