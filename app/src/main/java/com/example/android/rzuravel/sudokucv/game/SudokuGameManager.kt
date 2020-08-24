package com.example.android.rzuravel.sudokucv.game

import com.example.android.rzuravel.sudokucv.board.SudokuBoxViewModel

object SudokuGameManager {
    private var rows = List(9) { mutableListOf<SudokuBoxViewModel>() }
    private var columns = List(9) { mutableListOf<SudokuBoxViewModel>() }
    private var subregions = List(9) { mutableListOf<SudokuBoxViewModel>() }
    private var board = List(9) { MutableList<SudokuBoxViewModel?>(9) { null } }

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