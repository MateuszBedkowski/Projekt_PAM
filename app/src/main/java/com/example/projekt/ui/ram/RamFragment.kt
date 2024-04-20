package com.example.projekt.ui.ram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projekt.databinding.FragmentRamBinding
import androidx.lifecycle.Observer

class RamFragment : Fragment() {

    private var _binding: FragmentRamBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRamBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(RamViewModel::class.java)

        viewModel.getRamInfo(requireContext())

        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textRam.text = it
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}