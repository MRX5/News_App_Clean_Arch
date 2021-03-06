package com.example.news_app.features.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news_app.R
import com.example.news_app.databinding.NewsLayoutBinding
import com.example.news_app.domain.model.News
import com.example.news_app.features.base.BaseViewHolder

class HomeAdapter(private val click:(news:News)->Unit): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val newsList= mutableListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return HomeViewHolder(DataBindingUtil.inflate(inflater, R.layout.news_layout,parent,false))
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount()=newsList.size

     fun setData(newsList:List<News>){
        this.newsList.addAll(newsList)
         notifyDataSetChanged()
    }
    fun clearData(){
        newsList.clear()
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: NewsLayoutBinding):
        BaseViewHolder<News, NewsLayoutBinding>(binding) {

        override fun bind(news:News) {
            binding.news=news
            itemView.setOnClickListener {
                click(news)
            }
        }
    }



}