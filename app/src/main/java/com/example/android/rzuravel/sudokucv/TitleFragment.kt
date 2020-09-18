package com.example.android.rzuravel.sudokucv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.rzuravel.sudokucv.databinding.TitleFragmentBinding

class TitleFragment : Fragment() {

    companion object {
        fun newInstance() = TitleFragment()
    }

    private lateinit var binding: TitleFragmentBinding
    private lateinit var viewModel: TitleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(TitleViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.title_fragment, container, false)

        binding.titleNewButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        }

        binding.titleUploadButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_cameraFragment)
        }

        return binding.root
    }
}