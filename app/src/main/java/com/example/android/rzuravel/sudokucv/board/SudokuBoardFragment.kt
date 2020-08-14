package com.example.android.rzuravel.sudokucv.board

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android.rzuravel.sudokucv.R
import com.example.android.rzuravel.sudokucv.databinding.SudokuBoardFragmentBinding

class SudokuBoardFragment : Fragment() {

    companion object {
        fun newInstance() = SudokuBoardFragment()
    }

    private lateinit var binding: SudokuBoardFragmentBinding
    private lateinit var viewModel: SudokuBoardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.sudoku_board_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(SudokuBoardViewModel::class.java)
        binding.viewModel = viewModel

        val transaction = childFragmentManager.beginTransaction()

        transaction.add(R.id.sudoku_subregion_1, SudokuSubregionFragment.newInstance(0))
        transaction.add(R.id.sudoku_subregion_2, SudokuSubregionFragment.newInstance(1))
        transaction.add(R.id.sudoku_subregion_3, SudokuSubregionFragment.newInstance(2))

        transaction.add(R.id.sudoku_subregion_4, SudokuSubregionFragment.newInstance(3))
        transaction.add(R.id.sudoku_subregion_5, SudokuSubregionFragment.newInstance(4))
        transaction.add(R.id.sudoku_subregion_6, SudokuSubregionFragment.newInstance(5))

        transaction.add(R.id.sudoku_subregion_7, SudokuSubregionFragment.newInstance(6))
        transaction.add(R.id.sudoku_subregion_8, SudokuSubregionFragment.newInstance(7))
        transaction.add(R.id.sudoku_subregion_9, SudokuSubregionFragment.newInstance(8))

        transaction.commit()

        return binding.root
    }

}