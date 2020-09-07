package com.example.android.rzuravel.sudokucv.game

import android.app.Application
import android.content.Context
import com.example.android.rzuravel.sudokucv.board.SudokuBoxViewModel
import com.example.android.rzuravel.sudokucv.database.SudokuBoxData
import com.example.android.rzuravel.sudokucv.database.SudokuBoxDatabase
import com.example.android.rzuravel.sudokucv.database.SudokuBoxDatabaseDao
import kotlin.coroutines.coroutineContext

object SudokuGameManager {
    private val rows = List(9) { mutableListOf<SudokuBoxViewModel>() }
    private val columns = List(9) { mutableListOf<SudokuBoxViewModel>() }
    private val subregions = List(9) { mutableListOf<SudokuBoxViewModel>() }
    private val board = List(9) { MutableList<SudokuBoxViewModel?>(9) { null } }
    private lateinit var sudokuBoxDatabaseDao: SudokuBoxDatabaseDao


    fun fillBlankBoard() {
        for (row in 0..8) {
            for (column in 0..8) {
                var box = SudokuBoxData(
                    boxId = row * 9 + column,
                    row = row,
                    column = column,
                    subregion = (row / 3) * 3 + (column / 3),
                    value = null,
                    hints = BooleanArray(9) { true })
                sudokuBoxDatabaseDao.insert(box)
            }
        }
    }

    fun addViewModel(vm: SudokuBoxViewModel, row: Int, column: Int, subregion: Int) {
        rows[row].add(vm)
        columns[column].add(vm)
        subregions[subregion].add(vm)

        board[row][column] = vm
    }

    fun onValueAdded(value: Int, row: Int, column: Int, subregion: Int) {
        rows[row].forEach {
            it.onRelatedSetValue(value)
        }

        columns[column].forEach {
            it.onRelatedSetValue(value)
        }

        subregions[subregion].forEach {
            it.onRelatedSetValue(value)
        }
    }
}