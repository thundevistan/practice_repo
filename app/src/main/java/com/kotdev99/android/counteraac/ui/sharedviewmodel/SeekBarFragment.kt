package com.kotdev99.android.counteraac.ui.sharedviewmodel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kotdev99.android.counteraac.R
import com.kotdev99.android.counteraac.databinding.FragmentSeekBarBinding
import com.kotdev99.android.counteraac.viewmodel.SharedViewModel

class SeekBarFragment : Fragment() {

	private lateinit var binding: FragmentSeekBarBinding
	private val sharedViewModel: SharedViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seek_bar, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView()
	}

	private fun initView() = with(binding) {

		seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
			override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
				sharedViewModel?.updateProgress(progress)
				Log.d("progress", progress.toString())
			}

			override fun onStartTrackingTouch(p0: SeekBar?) {
				// TODO("안 지우면 에러 발생함!!")
			}

			override fun onStopTrackingTouch(p0: SeekBar?) {
				// TODO("안 지우면 에러 발생함!!")
			}
		})

		/** 데이터 바인딩 */
//		sharedViewModel.progress.observe(requireActivity(), Observer {
//			seekBar.progress = it
//		})
		lifecycleOwner = requireActivity()
		viewModel = sharedViewModel
	}
}