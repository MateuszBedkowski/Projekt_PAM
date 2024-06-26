package com.example.projekt.ui.disk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekt.databinding.FragmentDiskBinding

class DiskFragment : Fragment() {

    private var _binding: FragmentDiskBinding? = null
    private lateinit var diskViewModel: DiskViewModel
    private lateinit var filesAdapter: FilesAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiskBinding.inflate(inflater, container, false)
        val root: View = binding.root

        diskViewModel = ViewModelProvider(this).get(DiskViewModel::class.java)
        diskViewModel.setContext(requireContext())

        val diskUsageTextView = binding.textDisk
        diskViewModel.diskSpaceInfo.observe(viewLifecycleOwner) { info ->
            diskUsageTextView.text = info
        }

        filesAdapter = FilesAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filesAdapter
        }

        // Observe fileInfoList from DiskViewModel
        diskViewModel.fileInfoList.observe(viewLifecycleOwner) { fileList: List<FileInfo> ->
            filesAdapter.submitList(fileList)
        }

        return root
    }
    override fun onStart() {
        super.onStart()
        onStartView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onStartView() {
        diskViewModel.fileInfoList.observe(viewLifecycleOwner) { fileList: List<FileInfo> ->
            filesAdapter.submitList(fileList)
        }
    }


}
