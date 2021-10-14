package com.example.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter (private val lst:ItemClickedFun): RecyclerView.Adapter<NewsViewholder>(){

    private val items:ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewholder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.itemnews,parent,false)
        val holder=NewsViewholder(view)
        view.setOnClickListener {
            lst.onItemClicked(items[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: NewsViewholder, position: Int) {
        val currentItem=items[position]
        holder.title.text=currentItem.title
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    fun update(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }

}

class NewsViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView =itemView.findViewById(R.id.title)
    val author: TextView =itemView.findViewById(R.id.author)
    val image: ImageView =itemView.findViewById(R.id.newsImage)
}

interface ItemClickedFun{
    fun onItemClicked(item:News)
}