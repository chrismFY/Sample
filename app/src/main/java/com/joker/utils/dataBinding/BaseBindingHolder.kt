package com.joker.utils.dataBinding

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseBindingHolder(view: View) : RecyclerView.ViewHolder(view) {
    var binding: ViewDataBinding? = null
}