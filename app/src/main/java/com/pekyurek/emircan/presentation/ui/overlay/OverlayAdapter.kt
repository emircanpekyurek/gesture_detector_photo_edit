package com.pekyurek.emircan.presentation.ui.overlay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pekyurek.emircan.databinding.ItemOverlayBinding
import com.pekyurek.emircan.presentation.core.data.model.response.candidate.overlay.Overlay

class OverlayAdapter(private val onItemClicked: (overlayImageUrl: String?) -> Unit) :
    RecyclerView.Adapter<OverlayAdapter.ViewHolder>() {

    private val list = mutableListOf<Overlay>()

    private var lastSelectedPosition = -1

    private val selectedImageStrokeWith = 8F
    private val unSelectedImageStrokeWith = 0F

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOverlayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setData(data: List<Overlay>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemOverlayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(overlay: Overlay) {
            binding.ivOverlay.strokeWidth =
                if (layoutPosition == lastSelectedPosition) selectedImageStrokeWith else unSelectedImageStrokeWith

            binding.overlay = overlay
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                val lastPosition = lastSelectedPosition
                notifyItemChanged(lastPosition)
                lastSelectedPosition = layoutPosition
                notifyItemChanged(layoutPosition)
                onItemClicked(overlay.overlayUrl)
            }
        }
    }
}