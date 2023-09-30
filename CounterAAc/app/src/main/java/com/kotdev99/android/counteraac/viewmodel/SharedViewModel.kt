package com.kotdev99.android.counteraac.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

	private val _progress = MutableLiveData<Int>()
	val progress: LiveData<Int>
		get() = _progress

	fun updateProgress(progress: Int) {
		_progress.value = progress
	}
}