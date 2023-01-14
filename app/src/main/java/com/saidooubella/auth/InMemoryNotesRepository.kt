package com.saidooubella.auth

import androidx.lifecycle.MutableLiveData

internal object InMemoryNotesRepository : NotesRepository {

    private val notes = MutableLiveData(emptyList<Note>())

    override fun loadNotes() = notes

    override fun loadNote(index: Int) = notes.value!![index]

    override fun changeNote(index: Int, note: Note) {
        val mutableNotes = notes.value!!.toMutableList()
        mutableNotes.removeAt(index)
        mutableNotes.add(index, note)
        notes.value = mutableNotes.toList()
    }

    override fun insertNote(note: Note) {
        notes.value = notes.value?.plus(note)
    }

    override fun removeNote(index: Int) {
        val mutableNotes = notes.value!!.toMutableList()
        mutableNotes.removeAt(index)
        notes.value = mutableNotes.toList()
    }
}