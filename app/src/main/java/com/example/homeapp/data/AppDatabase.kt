package com.example.homeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homeapp.data.dao.NoteDao
import com.example.homeapp.data.dao.MessageDao // Import baru
import com.example.homeapp.data.entity.NoteEntity
import com.example.homeapp.data.entity.MessageEntity // Import baru

@Database(
    entities = [NoteEntity::class, MessageEntity::class], // Tambah MessageEntity
    version = 2 // Naikkan versi menjadi 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun messageDao(): MessageDao // Tambah Dao baru disini

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // Menghindari crash saat upgrade versi dev
                    .build().also { INSTANCE = it }
            }
        }
    }
}