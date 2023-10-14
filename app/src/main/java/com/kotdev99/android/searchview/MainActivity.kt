package com.kotdev99.android.searchview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {

	private lateinit var searchView: SearchView
	private lateinit var resultTextView: TextView

	private val items = mutableListOf("어벤져스", "아이언맨", "배트맨2", "배구", "슈퍼맨", "원더우먼", "베이지")

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		searchView = findViewById(R.id.search_view)
		resultTextView = findViewById(R.id.textView)

		resultTextView.text = getResult()

		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				return false
			}

			override fun onQueryTextChange(newText: String?): Boolean {
				resultTextView.text = newText?.let { search(it) }
				return true
			}
		})
	}

	private fun search(query: String): String {
		val sb = StringBuilder()

		for (i in items) {
			if (i.lowercase().trim().contains(query)) {
				sb.append(i + "\n")
			}
		}

		return sb.toString()
	}

	private fun getResult(): String {
		val sb = StringBuilder()

		for (i in items) {
			sb.append(i + "\n")
		}

		return sb.toString()
	}
}