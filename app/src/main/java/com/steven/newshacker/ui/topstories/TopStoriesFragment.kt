package com.steven.newshacker.ui.topstories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.steven.newshacker.databinding.FragmentTopStoriesBinding

class TopStoriesFragment : Fragment() {

    private lateinit var topStoriesViewModel: TopStoriesViewModel
    private var _binding: FragmentTopStoriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        topStoriesViewModel =
            ViewModelProvider(this)[TopStoriesViewModel::class.java]

        _binding = FragmentTopStoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}