package com.kotdev99.android.motionlayoutpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

	private lateinit var handler: Handler
	private lateinit var logo: ImageView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		handler = Handler(mainLooper)
		logo = findViewById(R.id.logo)

		handler.postDelayed({
			kotlin.run {
				logo.callOnClick()
			}
		}, 2000)

		handler.postDelayed({
			kotlin.run {
				startActivity(DetailActivity.newIntent(this))
			}
		}, 3000)
	}
}