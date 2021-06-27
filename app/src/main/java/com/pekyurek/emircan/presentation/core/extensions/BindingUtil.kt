package com.pekyurek.emircan.presentation.core.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter("loadImageFromUrl")
fun loadImageFromUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter("setHasFixedSize")
fun setHasFixedSize(recyclerView: RecyclerView, hasFixedSize: Boolean) {
    recyclerView.setHasFixedSize(hasFixedSize)
}

@BindingAdapter("setLinearLayoutManager")
fun setLinearLayoutManager(recyclerView: RecyclerView, vertical: Boolean) {
    recyclerView.layoutManager = LinearLayoutManager(
        recyclerView.context,
        if (vertical) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL,
        false
    )
}
