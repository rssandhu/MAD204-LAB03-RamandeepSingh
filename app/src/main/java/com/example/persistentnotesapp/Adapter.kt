package com.example.lab3notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView Adapter for notes list
 */
class MyAdapter(
    private val notes: MutableList<String>,
    private val longClickListener: OnItemLongClickListener
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    /**
     * Interface for long click listener
     */
    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    /**
     * ViewHolder class to hold item views
     */
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNote: TextView = itemView.findViewById(R.id.textViewNote)

        init {
            itemView.setOnLongClickListener {
                longClickListener.onItemLongClick(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewNote.text = notes[position]
    }
}
