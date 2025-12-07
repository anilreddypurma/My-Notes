package com.example.mynotes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    private val onDelete: (NoteEntity) -> Unit
) : ListAdapter<NoteEntity, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    class NoteViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return NoteViewHolder(textView).apply {
            textView.setOnLongClickListener {
                onDelete(getItem(adapterPosition))
                true
            }
        }
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.textView.text = getItem(position).text
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity) = oldItem == newItem
    }
}
