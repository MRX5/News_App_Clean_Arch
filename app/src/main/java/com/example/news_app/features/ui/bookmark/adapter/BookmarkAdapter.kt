package com.example.news_app.features.ui.bookmark.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news_app.R
import com.example.news_app.databinding.SmallNewsLayoutBinding
import com.example.news_app.domain.model.News
import com.example.news_app.features.base.BaseViewHolder

class BookmarkAdapter(private val click:(news:News)->Unit): RecyclerView.Adapter<BookmarkAdapter.SmallViewHolder>() {

    private val newsList= mutableListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return SmallViewHolder(DataBindingUtil.inflate(inflater, R.layout.small_news_layout,parent,false))
    }

    override fun onBindViewHolder(holder: SmallViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount()=newsList.size

     fun setData(newsList:List<News>){
        this.newsList.addAll(newsList)
         notifyDataSetChanged()
    }

    inner class SmallViewHolder(private val binding:SmallNewsLayoutBinding):
        BaseViewHolder<News, SmallNewsLayoutBinding>(binding) {

        override fun bind(news:News) {
            binding.news=news
            itemView.setOnClickListener {
                click(news)
            }
        }
    }

}