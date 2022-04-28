package com.example.news_app.features.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.news_app.R
import com.example.news_app.databinding.LargeNewsLayoutBinding
import com.example.news_app.databinding.SmallNewsLayoutBinding
import com.example.news_app.domain.model.News
import com.example.news_app.features.base.BaseViewHolder
import kotlinx.coroutines.NonDisposableHandle.parent

class HomeAdapter(private val click:(news:News)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val newsList= mutableListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return if(viewType==1){
            LargeViewHolder(DataBindingUtil.inflate(inflater, R.layout.large_news_layout,parent,false))
        }else{
            SmallViewHolder(DataBindingUtil.inflate(inflater, R.layout.small_news_layout,parent,false))
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LargeViewHolder -> holder.bind(newsList[position])
            is SmallViewHolder -> holder.bind(newsList[position])
        }
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
    inner class LargeViewHolder(private val binding:LargeNewsLayoutBinding):
        BaseViewHolder<News, LargeNewsLayoutBinding>(binding) {

        override fun bind(news:News) {
            binding.news=news
            itemView.setOnClickListener {

            }
        }

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