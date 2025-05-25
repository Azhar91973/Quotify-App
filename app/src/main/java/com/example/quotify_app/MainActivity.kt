package com.example.quotify_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quotesapp.MainViewModel
import com.example.quotesapp.Quotes
import com.example.quotesapp.ViewModelFactory
import com.example.quotify_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewModelInstance: MainViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpClickListeners()

    }

    private fun setUpViewModel() {
        viewModelInstance =
            ViewModelProvider(this, ViewModelFactory(application))[MainViewModel::class.java]
        val quotes = viewModelInstance.getQuote()!!
        setQuotes(quotes)
    }

    fun setQuotes(quotes: Quotes) {
        binding.quoteText.text = quotes.quote
        binding.quoteAuthor.text = quotes.authorName
    }

    private fun setUpClickListeners() {
        with(binding) {
            previousBtn.setOnClickListener {
                setQuotes(viewModelInstance.previousQuote()!!)
            }
            nextBtn.setOnClickListener {
                setQuotes(viewModelInstance.nextQuote()!!)
            }
            shareBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain")
                intent.putExtra(Intent.EXTRA_TEXT, viewModelInstance.getQuote()?.quote)
                startActivity(intent)
            }
        }
    }

}