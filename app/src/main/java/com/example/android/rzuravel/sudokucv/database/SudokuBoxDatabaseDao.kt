package com.example.android.rzuravel.sudokucv.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SudokuBoxDatabaseDao {
    @Insert
    fun insert(box: SudokuBoxData)

    @Update
    fun update(box: SudokuBoxData)

    @Query("SELECT * FROM ${SudokuBoxDatabase.DB_NAME} WHERE boxId = :key")
    fun get(key: Int): SudokuBoxData?

    @Query("SELECT * FROM ${SudokuBoxDatabase.DB_NAME} WHERE `row` = :row AND `column` = :column")
    fun get(row: Int, column: Int): LiveData<SudokuBoxData?>

    @Query("DELETE FROM ${SudokuBoxDatabase.DB_NAME}")
    fun clear()

    @Query("SELECT * FROM ${SudokuBoxDatabase.DB_NAME} ORDER BY boxId ASC")
    fun getAllBoxes(): LiveData<List<SudokuBoxData>>
}