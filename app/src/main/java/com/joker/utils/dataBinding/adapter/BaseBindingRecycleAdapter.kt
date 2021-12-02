package com.joker.utils.dataBinding.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.joker.utils.dataBinding.BaseBindingHolder


open class BaseBindingRecycleAdapter<T>( dataBindingId: Int,layoutResId: Int,diffCallback: DiffUtil.ItemCallback<T>) :
    BaseBindingRecycleAdapterIAbstract<T>(dataBindingId, layoutResId,diffCallback) {
    override fun myOnCreateViewHolder(parent: ViewGroup?, viewType: Int, holder: BaseBindingHolder?) {}
    override fun myOnbinde(holder: BaseBindingHolder?, position: Int) {}
}
