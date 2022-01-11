package com.steven.newshacker.ui.beststories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mindorks.retrofit.coroutines.utils.Status
import com.steven.newshacker.adapter.StoryAdapter
import com.steven.newshacker.databinding.FragmentBestStoriesBinding
import com.steven.newshacker.listener.OnStoryItemInteractionListener
import com.steven.newshacker.model.StoryModel
import com.steven.newshacker.network.ApiHelper
import com.steven.newshacker.network.RetrofitBuilder
import com.steven.newshacker.ui.ViewModelFactory

class BestStoriesFragment : Fragment() {

    private lateinit var bestStoriesViewModel: BestStoriesViewModel
    private var _binding: FragmentBestStoriesBinding? = null

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
        bestStoriesViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[BestStoriesViewModel::class.java]
        _binding = FragmentBestStoriesBinding.inflate(inflater, container, false)
        setupUI()
        setupObservers()
        return binding.root
    }

    private fun setupUI() {
        binding.bestStoryList.adapter = storyAdapter
    }

    private fun setupObservers() {
        val storyList = ArrayList<StoryModel>()
        bestStoriesViewModel.getTopStoriesIDList(VALUE_TYPE_BEST_STORY).observe(viewLifecycleOwner, {
            it?.let { storyIdList ->
                when (storyIdList.status) {
                    Status.SUCCESS -> {
                        storyIdList.data?.forEachIndexed { index, l ->
                            bestStoriesViewModel.getTopStoriesById(l.toString())
                                .observe(viewLifecycleOwner, { storyResource ->
                                    storyResource?.let { story ->
                                        when (story.status) {
                                            Status.SUCCESS -> {
                                                storyList.add(story.data!!)
                                                binding.progressBar.visibility = View.GONE
                                                binding.bestStoryList.visibility = View.VISIBLE
                                                if (index == storyIdList.data.size - 1) {
                                                    storyAdapter.submitList(storyList)
                                                }
                                            }
                                            Status.ERROR   -> {
                                                binding.progressBar.visibility = View.GONE
                                                binding.bestStoryList.visibility = View.GONE
                                            }
                                            Status.LOADING -> {
                                                binding.progressBar.visibility = View.VISIBLE
                                                binding.bestStoryList.visibility = View.GONE
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

    companion object {
        private const val VALUE_TYPE_BEST_STORY = "beststories"
    }
}