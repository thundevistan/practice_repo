package com.kotdev99.android.youtubeapikotlin.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kotdev99.android.youtubeapikotlin.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {

	private lateinit var binding: FragmentVideoBinding
	private val viewModel: VideoViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentVideoBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		observer()
	}

	private fun observer() = with(binding) {
		viewModel.channel.observe(viewLifecycleOwner, Observer {
			if (it != null && it.items.isNotEmpty()) {
				it.items.forEach { channel ->
					textHome.text = channel.snippet.title
				}
			}
		})
	}
}