package com.danicktakam.demo3andm.recyclerextentions

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Android4Dev
 * Register [OnClickListener] on ViewHolder root view
 * @author Lokesh Desai
 * @param event callback function receiving root view, item position and type
 * @return returns this view holder
 */
fun <T : RecyclerView.ViewHolder> T.onClick(event: (view: View, position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(it, adapterPosition, itemViewType)
    }
    return this
}