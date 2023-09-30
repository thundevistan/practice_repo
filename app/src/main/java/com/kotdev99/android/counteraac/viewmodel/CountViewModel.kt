package com.kotdev99.android.counteraac.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountViewModel : ViewModel() {

	private val _count = MutableLiveData<Int>()
	val count: LiveData<Int>
		get() = _count

	init {
		_count.value = 0
	}

	fun increase() {
		_count.value = _count.value!! + 1
	}

	fun decrease() {
		_count.value = _count.value!! - 1
	}
}