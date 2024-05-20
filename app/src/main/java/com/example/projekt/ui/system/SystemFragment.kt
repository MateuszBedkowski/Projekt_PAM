package com.example.projekt.ui.system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projekt.databinding.FragmentSystemBinding

class SystemFragment : Fragment() {

    private var _binding: FragmentSystemBinding? = null
    private lateinit var systemViewModel: SystemViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize the ViewModel with the factory
        val context = requireContext()
        val factory = SystemViewModelFactory(context)
        systemViewModel = ViewModelProvider(this, factory).get(SystemViewModel::class.java)

        _binding = FragmentSystemBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSystem
        systemViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
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
        val textView: TextView = binding.textSystem
        systemViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }
}
