package com.saidooubella.auth

import androidx.lifecycle.LiveData

internal interface NotesRepository {
    fun loadNotes(): LiveData<List<Note>>
    fun loadNote(index: Int): Note
    fun changeNote(index: Int, note: Note)
    fun insertNote(note: Note)
    fun removeNote(index: Int)
}
