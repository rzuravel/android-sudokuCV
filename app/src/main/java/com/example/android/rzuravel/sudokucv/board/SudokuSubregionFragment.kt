package com.example.android.rzuravel.sudokucv.board

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.android.rzuravel.sudokucv.R
import com.example.android.rzuravel.sudokucv.databinding.SudokuBoxFragmentBinding
import com.example.android.rzuravel.sudokucv.databinding.SudokuSubregionFragmentBinding
import kotlinx.android.synthetic.main.sudoku_subregion_fragment.*

class SudokuSubregionFragment(inSubregion: Int) : Fragment() {

    companion object {
        fun newInstance(inSubregion: Int) = SudokuSubregionFragment(inSubregion)
    }

    private val subregion = inSubregion
    private lateinit var binding: SudokuSubregionFragmentBinding
    private lateinit var viewModel: SudokuSubregionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.sudoku_subregion_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(SudokuSubregionViewModel::class.java)
        binding.viewModel = viewModel

        val transaction = childFragmentManager.beginTransaction()

        val startRow = subregionStartRow(subregion)
        val startColumn = subregionStartColumn(subregion)

        transaction.add(R.id.sudoku_box_1, SudokuBoxFragment.newInstance(startRow, startColumn))
        transaction.add(R.id.sudoku_box_2, SudokuBoxFragment.newInstance(startRow,startColumn + 1))
        transaction.add(R.id.sudoku_box_3, SudokuBoxFragment.newInstance(startRow,startColumn + 2))

        transaction.add(R.id.sudoku_box_4, SudokuBoxFragment.newInstance(startRow + 1, startColumn))
        transaction.add(R.id.sudoku_box_5, SudokuBoxFragment.newInstance(startRow + 1,startColumn + 1))
        transaction.add(R.id.sudoku_box_6, SudokuBoxFragment.newInstance(startRow + 1,startColumn + 2))

        transaction.add(R.id.sudoku_box_7, SudokuBoxFragment.newInstance(startRow + 2, startColumn))
        transaction.add(R.id.sudoku_box_8, SudokuBoxFragment.newInstance(startRow + 2, startColumn + 1))
        transaction.add(R.id.sudoku_box_9, SudokuBoxFragment.newInstance(startRow + 2, startColumn + 2))

        transaction.commit()

        return binding.root
    }

    private fun subregionStartRow(subregion: Int) : Int {
        return (subregion / 3) * 3
    }

    private fun subregionStartColumn(subregion: Int) : Int {
        return (subregion % 3) * 3
    }
}