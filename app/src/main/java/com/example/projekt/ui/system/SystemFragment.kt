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

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val systemViewModel =
                ViewModelProvider(this).get(SystemViewModel::class.java)

        _binding = FragmentSystemBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSystem
        systemViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}