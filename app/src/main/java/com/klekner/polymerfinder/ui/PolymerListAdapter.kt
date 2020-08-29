package com.klekner.polymerfinder.ui

import androidx.recyclerview.widget.DiffUtil
import com.klekner.polymerfinder.R
import com.klekner.polymerfinder.data.Polymer

class PolymerListAdapter(clickListener: OnItemClickListener<Polymer>) : DataBindingAdapter<Polymer>(clickListener, DiffCallback()) {

    override fun getItemViewType(position: Int) =
        R.layout.polymer_item_layout

    class DiffCallback : DiffUtil.ItemCallback<Polymer>() {
        override fun areItemsTheSame(oldItem: Polymer, newItem: Polymer): Boolean {
            val id1 = oldItem.id
            val id2 = newItem.id
            return id1 == id2
        }

        override fun areContentsTheSame(oldItem: Polymer, newItem: Polymer): Boolean {
            //todo update condition
            return oldItem.shortName == newItem.fullName && oldItem.casNumber == newItem.casNumber
        }
    }

}

interface OnItemClickListener<T> {
    fun onClick(id: T)
}