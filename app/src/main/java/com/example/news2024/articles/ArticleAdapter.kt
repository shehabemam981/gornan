package com.example.news2024.articles

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news2024.R
import com.example.news2024.model.Articles
import com.example.news2024.model.ArticlesItem
import com.google.android.material.imageview.ShapeableImageView

class ArticleAdapter(val articles: List<ArticlesItem?>?): RecyclerView.Adapter<ArticleAdapter.myViewHolder>() {

    class myViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    var articleImage = itemView.findViewById<ShapeableImageView>(R.id.articleImage)
    var articleTitle = itemView.findViewById<TextView>(R.id.articletitle)
    var articleText = itemView.findViewById<TextView>(R.id.articletext)
    var articleClock = itemView.findViewById<TextView>(R.id.articleclock)
        fun bind(articlesItem: ArticlesItem){
            articleText.text =articlesItem.content
            articleTitle.text=articlesItem.title
            articleClock.text = articlesItem.publishedAt
            Glide.with(itemView)
                .load(articlesItem.urlToImage)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.search)
                .into(articleImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
       val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.content_articles,parent,false)
        return myViewHolder(itemView)
    }

    override fun getItemCount(): Int =articles!!.size

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val item  = articles?.get(position)
        if (item != null) {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                setOnClickDetails?.showDetails(item)
                Log.e("dd","data")
            }
        }

    }
    var setOnClickDetails:onClickDetails?=null

    fun interface  onClickDetails{
       fun showDetails(articles:ArticlesItem)
    }
}