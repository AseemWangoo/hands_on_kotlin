package com.aseemwangoo.handsonkotlin.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDatabaseDao {
    @Query("SELECT * from my_todo_list")
    fun getAll(): LiveData<List<TodoItem>>

    @Query("SELECT * from my_todo_list where itemId = :id")
    fun getById(id: Int) : TodoItem?

    @Insert
    suspend fun insert(item:TodoItem)

    @Update
    fun update(item:TodoItem)

    @Delete
    fun delete(item:TodoItem)
}