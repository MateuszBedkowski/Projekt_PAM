package com.example.projekt.ui.cpu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projekt.databinding.FragmentCpuBinding

class CpuFragment : Fragment() {

    private var _binding: FragmentCpuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val cpuViewModel =
                ViewModelProvider(this).get(CpuViewModel::class.java)

        _binding = FragmentCpuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCpu
        cpuViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}