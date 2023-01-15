package com.saidooubella.auth.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.saidooubella.auth.models.Note
import com.saidooubella.auth.db.local.NotesDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class NewNoteViewModel(
    private val notesDao: NotesDao,
    private val editingIndex: Long?,
) : ViewModel() {

    private val mutableState = MutableStateFlow(Note())

    val state = mutableState.asStateFlow()

    init {
        if (editingIndex != null) {
            viewModelScope.launch {
                mutableState.update { notesDao.load(editingIndex) }
            }
        }
    }

    fun updateContent(
        title: String = mutableState.value.title,
        content: String = mutableState.value.content
    ) = mutableState.update {
        it.copy(title = title, content = content)
    }

    fun saveNote() {
        viewModelScope.launch { notesDao.insert(mutableState.value) }
    }

    class Factory(
        private val notesDao: NotesDao,
        private val editingIndex: Long?,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewNoteViewModel(notesDao, editingIndex) as T
        }
    }
}