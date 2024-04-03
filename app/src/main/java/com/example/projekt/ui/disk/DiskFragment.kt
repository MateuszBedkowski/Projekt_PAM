package com.example.projekt.ui.disk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projekt.databinding.FragmentDiskBinding

class DiskFragment : Fragment() {

    private var _binding: FragmentDiskBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val diskViewModel =
                ViewModelProvider(this).get(DiskViewModel::class.java)

        _binding = FragmentDiskBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDisk
        diskViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}