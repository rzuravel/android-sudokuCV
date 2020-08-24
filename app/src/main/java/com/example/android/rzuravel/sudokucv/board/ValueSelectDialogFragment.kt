package com.example.android.rzuravel.sudokucv.board

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.rzuravel.sudokucv.R
import com.example.android.rzuravel.sudokucv.databinding.ValueSelectFragmentBinding

class ValueSelectDialogFragment(inHints: BooleanArray) : DialogFragment() {

    companion object {
        fun newInstance(hints: BooleanArray) = ValueSelectDialogFragment(hints)
        const val VALUE_SELECT_INT = "VALUE_SELECT_INT"
        const val VALUE_SELECT_HINTS = "VALUE_SELECT_HINTS"
    }

    private val hints = inHints
    private lateinit var viewModel: ValueSelectViewModel
    private lateinit var binding: ValueSelectFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.value_select_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this, ValueSelectViewModelFactory(hints)).get(ValueSelectViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.valueSelectEvent.observe(viewLifecycleOwner, { finished ->
            if (finished) onClick()
        })

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        return dialog
    }

    override fun onDestroy() {
        val bundle = Bundle()
        bundle.putInt(VALUE_SELECT_INT, viewModel.value.value?:0)
        bundle.putBooleanArray(VALUE_SELECT_HINTS, viewModel.hints.value)
        val intent = Intent()
        intent.putExtras(bundle)
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)

        super.onDestroy()
    }

    private fun onClick() {
        this.dismiss()
    }
}