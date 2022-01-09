package com.steven.newshacker.ui.newstories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.steven.newshacker.databinding.FragmentNewStoriesBinding

class NewStoriesFragment : Fragment() {

    private lateinit var newsStoriesViewModel: NewsStoriesViewModel
    private var _binding: FragmentNewStoriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        newsStoriesViewModel =
            ViewModelProvider(this)[NewsStoriesViewModel::class.java]

        _binding = FragmentNewStoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}