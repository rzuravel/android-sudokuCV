package com.example.android.rzuravel.sudokucv.board


import android.util.Log
import androidx.lifecycle.*
import java.lang.IllegalArgumentException

class SudokuBoxViewModel(inRow:Int, inColumn:Int) : ViewModel() {
    private val _value = MutableLiveData<Int>()
    val value: LiveData<Int>
        get() = _value

    private val _hints = MutableLiveData<BooleanArray>(BooleanArray(9) { false })
    val hints: LiveData<BooleanArray>
        get() = _hints

    private val _row = inRow
    private val _column = inColumn

    private val _onClickEvent = MutableLiveData<Boolean>()
    val onClickEvent: LiveData<Boolean>
        get() = _onClickEvent

    fun onClick() {
        _onClickEvent.value = true
        onClickFinished()
    }

    private fun onClickFinished() {
        _onClickEvent.value = false
    }

    fun setValue(incomingValue: Int) {
        when (incomingValue) {
            0 -> {
                _value.value = null
            }
            else -> {
                _value.value = incomingValue
                _hints.value = BooleanArray(9) { false }
            }
        }
    }

    fun setHints(incomingHints: BooleanArray) {
        if (_value.value == null) _hints.value = incomingHints
    }
}

class SudokuBoxViewModelFactory(private val row: Int, private val column: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SudokuBoxViewModel::class.java)) {
            return SudokuBoxViewModel(row, column) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}