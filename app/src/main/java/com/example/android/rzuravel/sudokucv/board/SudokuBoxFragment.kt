package com.example.android.rzuravel.sudokucv.board

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.rzuravel.sudokucv.R
import com.example.android.rzuravel.sudokucv.board.ValueSelectDialogFragment.Companion.VALUE_SELECT_INT
import com.example.android.rzuravel.sudokucv.databinding.SudokuBoxFragmentBinding
import kotlinx.android.synthetic.main.sudoku_box_fragment.*

class SudokuBoxFragment(inRow: Int, inColumn: Int, inSubregion: Int) : Fragment() {

    companion object {
        fun newInstance(row: Int, column: Int, subregion: Int) = SudokuBoxFragment(row, column, subregion)
    }

    private val row = inRow
    private val column = inColumn
    private val subregion = inSubregion
    private lateinit var viewModel: SudokuBoxViewModel
    private lateinit var binding: SudokuBoxFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.sudoku_box_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this, SudokuBoxViewModelFactory(row, column, subregion)).get(SudokuBoxViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.onClickEvent.observe(viewLifecycleOwner, Observer { finished ->
            if (finished) onClick()
        })

        return binding.root
    }

    private fun onClick() {
        createValueSelectDialog()
    }

    private fun createValueSelectDialog() {
        val viewSelectFragment = ValueSelectDialogFragment.newInstance(viewModel.hints.value!!)
        viewSelectFragment.setTargetFragment(this, targetRequestCode)
        viewSelectFragment.show(parentFragmentManager, ValueSelectDialogFragment.VALUE_SELECT_INT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == targetRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                if (data?.extras?.containsKey(ValueSelectDialogFragment.VALUE_SELECT_HINTS)!!) {
                    val incomingHints = data.extras?.getBooleanArray(ValueSelectDialogFragment.VALUE_SELECT_HINTS)
                    if (incomingHints != null && incomingHints.isEmpty().not()) {
                        viewModel.setHints(incomingHints)
                    }
                }

                if (data?.extras?.containsKey(ValueSelectDialogFragment.VALUE_SELECT_INT)!!) {
                    val incomingValue = data.extras?.getInt(ValueSelectDialogFragment.VALUE_SELECT_INT)
                    if (incomingValue != null) {
                        viewModel.setValue(incomingValue)
                    }
                }
            }
        }
    }

}