package com.kotdev99.android.youtubeapikotlin.ui.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.kotdev99.android.youtubeapikotlin.databinding.FragmentChannelBinding

class ChannelFragment : Fragment() {

	private var _binding: FragmentChannelBinding? = null
	private val binding get() = _binding!!
	private val viewModel: ChannelViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.channel.observe(viewLifecycleOwner) {
			if (it != null && it.items.isNotEmpty()) {
				it.items.forEach { channel ->
					binding.tvChannelName.text = channel.snippet.title
					binding.tvChannelDescription.text = channel.snippet.description
					Glide
						.with(requireActivity())
						.load(channel.branding.image.bannerUri)
						.centerCrop()
						.into(binding.ivChannelBanner)

					Glide
						.with(requireActivity())
						.load(channel.snippet.thumbnail.high.url)
						.into(binding.ivLogo)
				}
			}
		}

		viewModel.isLoading.observe(viewLifecycleOwner) {
			if (it) {
				binding.progressBar.visibility = View.VISIBLE
			} else {
				binding.progressBar.visibility = View.GONE
			}
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentChannelBinding.inflate(inflater, container, false)
		return binding.root
	}
}