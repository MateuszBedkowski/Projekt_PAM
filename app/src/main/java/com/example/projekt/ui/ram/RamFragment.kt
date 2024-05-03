package com.example.projekt.ui.ram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projekt.databinding.FragmentRamBinding
import com.example.projekt.ui.cpu.RamViewModel

class RamFragment : Fragment() {

    private var _binding: FragmentRamBinding? = null
    private val binding get() = _binding!!

    private lateinit var ramViewModel: RamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRamBinding.inflate(inflater, container, false)

        ramViewModel = ViewModelProvider(this).get(RamViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = binding.textRam

        ramViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        ramViewModel.getRamInfo(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
