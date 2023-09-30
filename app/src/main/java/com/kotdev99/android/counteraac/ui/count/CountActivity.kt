package com.kotdev99.android.counteraac.ui.count

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kotdev99.android.counteraac.R
import com.kotdev99.android.counteraac.databinding.ActivityCountBinding
import com.kotdev99.android.counteraac.viewmodel.CountViewModel

class CountActivity : AppCompatActivity() {

	private lateinit var binding: ActivityCountBinding
	private val countViewModel: CountViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_count)

		initView()
	}

	private fun initView() = with(binding) {

		/** 데이터 바인딩으로 xml의 onClick 속성에 직접 구현 */
//		addFab.setOnClickListener {
//			viewModel?.increase()
//		}
//
//		rmFab.setOnClickListener {
//			viewModel?.decrease()
//		}

		/**데이터 바인딩으로 xml의 <data> 태그에 직접 구현 */
//		viewModel.count.observe(this@MainActivity, Observer {
//			countTv.text = it.toString()
//			Log.d("observer", it.toString())
//		})
		// 데이터를 xml 에 직접 넘기는 대신 아래 2가지는 작성해야 한다
		lifecycleOwner = this@CountActivity
		viewModel = countViewModel
	}
}