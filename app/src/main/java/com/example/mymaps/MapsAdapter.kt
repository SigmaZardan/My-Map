package com.example.mymaps

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.mymaps.models.UserMap

private const val TAG = "MapsAdapter"

class MapsAdapter(
    private val context: Context,
    private val userMaps: List<UserMap>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<MapsAdapter.MapsAdapterViewHolder>() {
    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsAdapterViewHolder {
        val view =
            LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MapsAdapterViewHolder(view)
    }

    override fun getItemCount() = userMaps.size

    override fun onBindViewHolder(holder: MapsAdapterViewHolder, position: Int) {
        val userMap = userMaps[position]
        val textViewTitle = holder.itemView.findViewById<TextView>(android.R.id.text1)
        textViewTitle.text = userMap.title

        holder.itemView.setOnClickListener {
            Log.i(TAG, "Clicked on ${position}th title")
            // tapping the item i want to send the position of the map back to the main activity
            onClickListener.onItemClick(position)

        }
    }

    inner class MapsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
