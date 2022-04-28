package com.example.news_app.features.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<MODEL,T:ViewDataBinding>(viewBinding:T):RecyclerView.ViewHolder(viewBinding.root) {
    abstract fun bind(model:MODEL)
}