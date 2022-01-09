package com.steven.newshacker.ui.beststories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.steven.newshacker.databinding.FragmentBestStoriesBinding
import com.steven.newshacker.ui.more.MoreViewModel

class BestStoriesFragment : Fragment() {

    private lateinit var moreViewModel: MoreViewModel
    private var _binding: FragmentBestStoriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        moreViewModel =
            ViewModelProvider(this)[MoreViewModel::class.java]

        _binding = FragmentBestStoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}