package com.steven.newshacker.ui.topstories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mindorks.retrofit.coroutines.utils.Status
import com.steven.newshacker.adapter.StoryAdapter
import com.steven.newshacker.databinding.FragmentTopStoriesBinding
import com.steven.newshacker.listener.OnStoryItemInteractionListener
import com.steven.newshacker.model.StoryModel
import com.steven.newshacker.network.ApiHelper
import com.steven.newshacker.network.RetrofitBuilder
import com.steven.newshacker.ui.ViewModelFactory

class TopStoriesFragment : Fragment() {

    private lateinit var topStoriesViewModel: TopStoriesViewModel
    private var _binding: FragmentTopStoriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val storyAdapter by lazy {
        StoryAdapter(
            object : OnStoryItemInteractionListener {
                override fun onCommentClicked(story: StoryModel) {
                    TODO("Not yet implemented")
                }

                override fun onItemClicked(story: StoryModel) {
                    TODO("Not yet implemented")
                }

                override fun onArticleClicked(story: StoryModel) {
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
        topStoriesViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[TopStoriesViewModel::class.java]
        _binding = FragmentTopStoriesBinding.inflate(inflater, container, false)
        setupUI()
        setupObservers()
        return binding.root
    }


    private fun setupUI() {
        binding.topStoryList.adapter = storyAdapter
    }

    private fun setupObservers() {
        val storyList = ArrayList<StoryModel>()
        topStoriesViewModel.getTopStoriesIDList().observe(viewLifecycleOwner, {
            it?.let { storyIdList ->
                when (storyIdList.status) {
                    Status.SUCCESS -> {
                        storyIdList.data?.forEachIndexed { index, l ->
                            topStoriesViewModel.getStoriesById(l.toString())
                                .observe(viewLifecycleOwner, { storyResource ->
                                    storyResource?.let { story ->
                                        when (story.status) {
                                            Status.SUCCESS -> {
                                                storyList.add(story.data!!)
                                                binding.progressBar.visibility = View.GONE
                                                binding.topStoryList.visibility = View.VISIBLE
                                                if (index == storyIdList.data.size - 1) {
                                                    storyAdapter.submitList(storyList)
                                                }
                                            }
                                            Status.ERROR   -> {
                                                binding.progressBar.visibility = View.GONE
                                                binding.topStoryList.visibility = View.GONE
                                            }
                                            Status.LOADING -> {
                                                binding.progressBar.visibility = View.VISIBLE
                                                binding.topStoryList.visibility = View.GONE
                                            }
                                        }
                                    }
                                })
                        }
                    }
                    Status.ERROR   -> {

                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}