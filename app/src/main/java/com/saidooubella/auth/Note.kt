package com.saidooubella.auth

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "notes")
internal data class Note(
    @PrimaryKey(autoGenerate = true)
    internal val id: Long = 0,
    internal val title: String = "",
    internal val content: String = "",
)

@Dao
internal interface NotesDao {

    @Query("SELECT * FROM notes")
    fun loadAll(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun load(id: Long): Note
}

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
