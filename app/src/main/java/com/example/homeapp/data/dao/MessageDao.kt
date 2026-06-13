package com.example.homeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.homeapp.data.entity.MessageEntity

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    suspend fun getAll(): List<MessageEntity>

    @Insert
    suspend fun insert(message: MessageEntity)

    @Delete
    suspend fun delete(message: MessageEntity)
}