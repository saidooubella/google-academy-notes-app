package com.saidooubella.auth.models

import androidx.room.*

@Entity(tableName = "notes")
internal data class Note(
    @PrimaryKey(autoGenerate = true)
    internal val id: Long = 0,
    internal val title: String = "",
    internal val content: String = "",
)
