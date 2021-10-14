package com.example.news

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity() ,ItemClickedFun{

    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView=findViewById<RecyclerView>(R.id.newsList)
        recyclerView.layoutManager= LinearLayoutManager(this)
        fetchData()
        mAdapter= NewsListAdapter(this)
        recyclerView.adapter=mAdapter

    }

    private fun fetchData(){
        val url="https://api.currentsapi.services/v1/latest-news?category=game&apiKey=zCvi2QJ22Aj337UHT2YdPnDGGsIev02iU2O3FG4-C3SoY-Yp"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val jsonArray=response.getJSONArray("news")
                val newsArray= ArrayList<News>()

                for (i in 0 until jsonArray.length()){
                    val news=jsonArray.getJSONObject(i)
                    val newsObject=News(
                        news.getString("title"),
                        news.getString("author"),
                        news.getString("url"),
                        news.getString("image")
                    )
                    newsArray.add(newsObject)
                }

                mAdapter.update(newsArray)
            },
            { error ->
                Log.d("error", error.toString())
            }
        )

        // Add the request to the RequestQueue.

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder= CustomTabsIntent.Builder()
        val customTabsIntent  = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}