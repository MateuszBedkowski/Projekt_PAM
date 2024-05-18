package com.example.projekt.ui.ram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.databinding.FragmentRamBinding

class RamFragment : Fragment() {

    private var _binding: FragmentRamBinding? = null
    private lateinit var ramViewModel: RamViewModel
    private lateinit var processesAdapter: ProcessesAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ramViewModel = ViewModelProvider(this).get(RamViewModel::class.java)

        _binding = FragmentRamBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRam

        // Call refreshRamInfo with the context
        ramViewModel.refreshRamInfo(requireContext())

        ramViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Set up RecyclerView for processes
        val recyclerView: RecyclerView = binding.processesListRam
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        processesAdapter = ProcessesAdapter()
        recyclerView.adapter = processesAdapter

        // Observe processes info from ViewModel
        ramViewModel.RamprocessesInfo.observe(viewLifecycleOwner) { processes ->
            processesAdapter.submitList(processes)
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
