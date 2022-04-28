package com.example.news_app.features.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class BaseFragment<T:ViewDataBinding>(@LayoutRes private val resourceId:Int):Fragment(){
    private var _binding:T? =null
    val binding:T get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=DataBindingUtil.inflate(inflater,resourceId,container,false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}