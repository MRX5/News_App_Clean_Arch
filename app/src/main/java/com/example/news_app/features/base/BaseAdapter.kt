package com.example.news_app.features.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<model:List<Any>,VIEW_BINDING: ViewDataBinding,ViewHolder:BaseViewHolder<model,VIEW_BINDING>>() {

}