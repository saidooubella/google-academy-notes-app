package com.saidooubella.auth.db.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saidooubella.auth.models.Note

@Database(entities = [Note::class], exportSchema = false, version = 1)
abstract class NotesDatabase : RoomDatabase() {

    internal abstract val notesDao: NotesDao

    companion object {

        private val LOCK = Any()

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(LOCK) {
                Room.databaseBuilder(context, NotesDatabase::class.java, "notes_keeper")
                    .build().also { INSTANCE = it }
            }
        }
    }
}