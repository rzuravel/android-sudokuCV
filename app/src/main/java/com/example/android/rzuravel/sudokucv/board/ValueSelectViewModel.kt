package com.example.android.rzuravel.sudokucv.board

import androidx.lifecycle.*
import java.lang.IllegalArgumentException

class ValueSelectViewModel(inHints: BooleanArray) : ViewModel() {
    private val _valueSelectEvent = MutableLiveData<Boolean>()
    val valueSelectEvent: LiveData<Boolean>
        get() = _valueSelectEvent

    private val _value = MutableLiveData<Int>(0)
    val value: LiveData<Int>
        get() = _value

    private val _hintToggle = MutableLiveData<Boolean>(false)
    val hintToggle: LiveData<Boolean>
        get() = _hintToggle

    private val _hints = MutableLiveData<BooleanArray>(inHints)
    val hints: LiveData<BooleanArray>
        get() = _hints

    fun onClick(selected: Int) {
        when {
            hintToggle.value!! -> {
                if (selected > 0) individualHintToggle(selected)
                else _valueSelectEvent.value = true
            }
            else -> {
                _value.value = selected
                _valueSelectEvent.value = true
            }
        }
        onClickCompleted()
    }

    private fun onClickCompleted() {
        _valueSelectEvent.value = false
    }

    fun onHintToggle() {
        _hintToggle.value = _hintToggle.value?.not()
    }

    private fun individualHintToggle(hintValue: Int) {
        if (hintValue > 0) {
            _hints.value?.set(hintValue-1, _hints.value?.get(hintValue-1)?.not() ?: false)
            _hints.value = _hints.value
        }
    }
}

class ValueSelectViewModelFactory(private val hints: BooleanArray) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ValueSelectViewModel::class.java)) {
            return ValueSelectViewModel(hints) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}