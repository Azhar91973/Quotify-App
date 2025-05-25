package com.example.quotesapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import org.json.JSONArray
import java.io.IOException

class MainViewModel(val context: Context) : ViewModel() {
    private var quotesList: Array<Quotes> = emptyArray()
    private var index = 0

    init {
        // initializing the quotes list
        quotesList = loadQuotesFromAssets()
    }

    private fun loadQuotesFromAssets(): Array<Quotes> {
        try {
            // opening the file

            val inputStream = context.assets.open("quotes.json")
            // finding the size of the file
            val size: Int = inputStream.available()
            // creating the buffer to store the data
            val buffer = ByteArray(size)
            // storing the data into buffer
            inputStream.read(buffer)
            // now closing the file
            inputStream.close()
            // converting the buffer data into string
            val json = String(buffer, Charsets.UTF_8)


            val quotesList = ArrayList<Quotes>()
            // converting the string into jsonarray
            val jsonArray = JSONArray(json)
            // now fetching the data from jsonarray and buiding the list of Quotes
            for (i in 0 until jsonArray.length()) {
                val jsonQuote = jsonArray.getJSONObject(i)
                val text = jsonQuote.getString("text")
                val author = jsonQuote.getString("author")

                val quote = Quotes(text, author)
                quotesList.add(quote)
            }
            // returning the Quotes list
            return quotesList.toTypedArray()
        } catch (e: IOException) {
            Log.e("ViewModel", "Error loading quotes", e)
            return emptyArray()
        }
    }


    fun getQuote(): Quotes? {
        return quotesList.getOrNull(index)
    }

    fun nextQuote(): Quotes? {
        index = (index + 1) % quotesList.size
        return quotesList.getOrNull(index)
    }

    fun previousQuote(): Quotes? {
        index = (index - 1 + quotesList.size) % quotesList.size
        return quotesList.getOrNull(index)
    }
}
