package com.example.android.rzuravel.sudokucv.database

import androidx.lifecycle.LiveData

class SudokuBoxRepository(private val sudokuBoxDao: SudokuBoxDatabaseDao) {
    val allBoxes: LiveData<List<SudokuBoxData>> = sudokuBoxDao.getAllBoxes()

    suspend fun insert(sudokuBox: SudokuBoxData) {
        sudokuBoxDao.insert(sudokuBox)
    }
}