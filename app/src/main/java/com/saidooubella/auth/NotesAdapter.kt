package com.saidooubella.auth

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saidooubella.auth.databinding.NoteItemBinding

internal class NotesAdapter(
    private val callback: OnItemListener
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    internal var notes: List<Note> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(NoteItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    internal inner class ViewHolder(
        private val binding: NoteItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                callback.onClick(notes[adapterPosition].id)
            }
            binding.root.setOnLongClickListener {
                callback.onLongClick(notes[adapterPosition].id)
                true
            }
        }

        fun bind(note: Note) {
            binding.noteTitle.text = note.title
            binding.noteBody.text = note.content
        }
    }

    internal interface OnItemListener {
        fun onLongClick(id: Long)
        fun onClick(id: Long)
    }
}