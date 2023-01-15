package com.saidooubella.auth.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.saidooubella.auth.db.local.NotesDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

internal class HomeViewModel(private val notesDao: NotesDao) : ViewModel() {

    private val mutableQuery = MutableStateFlow("")

    val notes = mutableQuery.flatMapLatest { notesDao.loadAll(it) }

    val query = mutableQuery.asStateFlow()

    fun removeNote(id: Long) {
        viewModelScope.launch { notesDao.delete(id) }
    }

    fun setQuery(query: String) {
        mutableQuery.value = query
    }

    class Factory(private val notesDao: NotesDao) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(notesDao) as T
        }
    }
}