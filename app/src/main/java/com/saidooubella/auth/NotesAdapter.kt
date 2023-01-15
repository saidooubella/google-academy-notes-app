package com.saidooubella.auth

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saidooubella.auth.databinding.NoteItemBinding

internal class NotesAdapter(
    private val callback: OnItemLongClicked
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    internal var notes: List<Note> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(NoteItemBinding.inflate(inflater, parent, false), callback)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    internal class ViewHolder(
        private val binding: NoteItemBinding,
        private val callback: OnItemLongClicked,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                callback.onClick(adapterPosition)
            }
            binding.root.setOnLongClickListener {
                callback.onLongClick(adapterPosition)
                true
            }
        }

        fun bind(note: Note) {
            binding.noteTitle.text = note.tile
            binding.noteBody.text = note.content
        }
    }

    internal interface OnItemLongClicked {
        fun onLongClick(index: Int)
        fun onClick(index: Int)
    }
}