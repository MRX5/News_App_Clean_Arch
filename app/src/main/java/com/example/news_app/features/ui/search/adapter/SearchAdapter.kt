package com.example.news_app.features.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news_app.R
import com.example.news_app.databinding.NewsLayoutBinding
import com.example.news_app.domain.model.News
import com.example.news_app.features.base.BaseViewHolder

class SearchAdapter(private val click:(news: News)->Unit): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val newsList= mutableListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        return SearchViewHolder(DataBindingUtil.inflate(inflater, R.layout.news_layout,parent,false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount()=newsList.size

    fun setData(newsList:List<News>){
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding: NewsLayoutBinding):
        BaseViewHolder<News, NewsLayoutBinding>(binding) {

        override fun bind(news: News) {
            binding.news=news
            itemView.setOnClickListener {
                click(news)
            }
        }
    }

}