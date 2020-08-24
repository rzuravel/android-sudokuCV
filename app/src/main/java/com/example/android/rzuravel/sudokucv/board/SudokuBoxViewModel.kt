package com.example.android.rzuravel.sudokucv.board


import androidx.lifecycle.*
import com.example.android.rzuravel.sudokucv.game.SudokuGameManager
import java.lang.IllegalArgumentException

class SudokuBoxViewModel(inRow: Int, inColumn: Int, inSubregion: Int) : ViewModel() {
    private val _value = MutableLiveData<Int>()
    val value: LiveData<Int>
        get() = _value

    private val _hints = MutableLiveData(BooleanArray(9) { true })
    val hints: LiveData<BooleanArray>
        get() = _hints

    private val _row = inRow
    private val _column = inColumn
    private val _subregion = inSubregion

    private val _onClickEvent = MutableLiveData<Boolean>()
    val onClickEvent: LiveData<Boolean>
        get() = _onClickEvent

    init {
        SudokuGameManager.addViewModel(this, _row, _column, _subregion)
    }

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

                SudokuGameManager.onValueAdded(incomingValue, _row, _column, _subregion)
            }
        }
    }

    fun onRelatedSetValue(relatedValue: Int) {
        _hints.value?.set(relatedValue-1, false)
        _hints.value = _hints.value
    }

    fun setHints(incomingHints: BooleanArray) {
        if (_value.value == null) _hints.value = incomingHints
    }
}

class SudokuBoxViewModelFactory(private val row: Int, private val column: Int, private val subregion: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SudokuBoxViewModel::class.java)) {
            return SudokuBoxViewModel(row, column, subregion) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}