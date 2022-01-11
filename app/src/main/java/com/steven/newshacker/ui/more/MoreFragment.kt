package com.steven.newshacker.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.steven.newshacker.adapter.MoreOptionAdapter
import com.steven.newshacker.adapter.StoryAdapter
import com.steven.newshacker.databinding.FragmentMoreBinding
import com.steven.newshacker.listener.OnMoreItemInteractionListener
import com.steven.newshacker.listener.OnStoryItemInteractionListener
import com.steven.newshacker.model.MoreOptionModel
import com.steven.newshacker.model.StoryModel

class MoreFragment : Fragment() {

    private lateinit var moreViewModel: MoreViewModel
    private var _binding: FragmentMoreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val moreOptionAdapter by lazy {
        MoreOptionAdapter(
            object : OnMoreItemInteractionListener {
                override fun onItemClicked(option: MoreOptionModel) {
                    TODO("Not yet implemented")
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        moreViewModel =
            ViewModelProvider(this)[MoreViewModel::class.java]
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        setupUI()
        setUpObserver()
        return binding.root
    }

    private fun setupUI() {
        binding.moreOptionList.adapter = moreOptionAdapter
    }

    private fun setUpObserver() {
        moreViewModel.optionList.observe(viewLifecycleOwner, {
            moreOptionAdapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}