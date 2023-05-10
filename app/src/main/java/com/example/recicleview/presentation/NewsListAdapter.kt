package com.example.recicleview.presentation

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.recicleview.R
import com.example.recicleview.data.News

class NewsListAdapter() : ListAdapter<News,NewListViewHolder> (NewsListAdapter){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewListViewHolder {
        val view: View = LayoutInflater
            .from(parent.context).
            inflate(R.layout.item_news, parent,false)
        return  NewListViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewListViewHolder, position: Int) {
       val news =getItem(position)
        holder.bind(news)
    }

    companion object: DiffUtil.ItemCallback<News>() {


        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.imUrl == newItem.imUrl

        }
    }


}

class NewListViewHolder(
    private val  view: View
    ) : RecyclerView.ViewHolder(view){
        private val tvTitle = view.findViewById<TextView>(R.id.tv_news_title)
        private val imgNews= view.findViewById<ImageView>(R.id.iv_news)

        fun bind(
             news: News
        ){
            tvTitle.text = news.title
            imgNews.load(news.imUrl){
                transformations(RoundedCornersTransformation(12f))
            }

        }
    }


