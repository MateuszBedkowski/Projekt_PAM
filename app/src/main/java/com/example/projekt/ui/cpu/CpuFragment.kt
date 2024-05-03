package com.example.projekt.ui.cpu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.databinding.FragmentCpuBinding

class CpuFragment : Fragment() {

    private var _binding: FragmentCpuBinding? = null
    private lateinit var cpuViewModel: CpuViewModel
    private lateinit var processesAdapter: ProcessesAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cpuViewModel = ViewModelProvider(this).get(CpuViewModel::class.java)
        Log.d("CpuFragment", "ViewModel initialized: $cpuViewModel")

        _binding = FragmentCpuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCpu
        cpuViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Set up RecyclerView for processes
        val recyclerView: RecyclerView = binding.processesList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        processesAdapter = ProcessesAdapter()
        recyclerView.adapter = processesAdapter

        // Observe processes info from ViewModel
        cpuViewModel.processesInfo.observe(viewLifecycleOwner) { processes ->
            processesAdapter.submitList(processes)
            Log.d("CpuFragment", "Processes observed: $processes")
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
