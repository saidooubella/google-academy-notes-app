package com.saidooubella.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

private fun populateDummyData(repository: NotesRepository) {
    repository.insertNote(
        Note(
            "Hello world world world world world world world world world world world world",
            "This an introduction to android development development development development development development development development development"
        )
    )
    repository.insertNote(Note("Hello world", "This an introduction to android development"))
    repository.insertNote(
        Note(
            "Hello world world",
            "This an introduction to android development development"
        )
    )
    repository.insertNote(
        Note(
            "Hello world world world world world world world world world world world world",
            "This an introduction to android development development development development development development development development development"
        )
    )
    repository.insertNote(
        Note(
            "Hello world world world",
            "This an introduction to android development development development"
        )
    )
    repository.insertNote(
        Note(
            "Hello world world world world",
            "This an introduction to android development development development"
        )
    )
    repository.insertNote(
        Note(
            "Hello world world world world world world world world world world world world",
            "This an introduction to android development development development development development development development development development"
        )
    )
    repository.insertNote(
        Note(
            "Hello world world world",
            "This an introduction to android development development development"
        )
    )
    repository.insertNote(
        Note(
            "Hello world world world world",
            "This an introduction to android development development development"
        )
    )
    repository.insertNote(
        Note(
            "Hello world world world world world world world world world world world world",
            "This an introduction to android development development development development development development development development development"
        )
    )
    repository.insertNote(
        Note(
            "Hello world world world",
            "This an introduction to android development development development"
        )
    )
    repository.insertNote(
        Note(
            "Hello world world world world",
            "This an introduction to android development development development"
        )
    )
    repository.insertNote(
        Note(
            "Hello world world world world world world world world world world world world",
            "This an introduction to android development development development development development development development development development"
        )
    )
}
