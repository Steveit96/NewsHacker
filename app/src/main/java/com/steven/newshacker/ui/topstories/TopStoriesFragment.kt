package com.steven.newshacker.ui.topstories

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.retrofit.coroutines.utils.Status
import com.steven.newshacker.adapter.StoryAdapter
import com.steven.newshacker.databinding.FragmentTopStoriesBinding
import com.steven.newshacker.listener.OnStoryItemInteractionListener
import com.steven.newshacker.model.StoryModel
import com.steven.newshacker.network.ApiHelper
import com.steven.newshacker.network.RetrofitBuilder
import com.steven.newshacker.ui.article.ArticleActivity
import com.steven.newshacker.ui.comments.CommentsActivity
import com.steven.newshacker.ui.ViewModelFactory
import com.steven.newshacker.utils.Constants

class TopStoriesFragment : Fragment() {

    private lateinit var topStoriesViewModel: TopStoriesViewModel
    private var _binding: FragmentTopStoriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding !!

    private var  firstIndex = 0

    private var lastIndex = 10

    private var cachedStoryIdList: List<Long> = ArrayList()

    private var cachedStoryList= ArrayList<StoryModel>()

    private var loading = false

    private val storyAdapter by lazy {
        StoryAdapter(
            object : OnStoryItemInteractionListener {
                override fun onCommentClicked(story: StoryModel) {
                    val commentBundle = bundleOf(
                        Constants.KEY_BUNDLE_OF__STORY_COMMENTS to story.kids)
                    startActivity(Intent(activity, CommentsActivity::class.java).apply {
                        putExtra(Constants.BUNDLE_STORY_TITLE, story.title)
                        putExtra(Constants.BUNDLE_STORY_AUTHOR, story.by)
                        putExtra(Constants.BUNDLE_STORY_CREATED_AT, story.time)
                        putExtra(Constants.BUNDLE_STORY_TYPE, story.type)
                        putExtra(Constants.BUNDLE_STORY_SCORE, story.score.toString())
                        putExtra(Constants.BUNDLE_STORY_URL, story.url)
                        putExtra(Constants.BUNDLE_STORY_COMMENTS, commentBundle)
                    })
                }

                override fun onArticleClicked(story: StoryModel) {
                    startActivity(Intent(activity, ArticleActivity::class.java).apply {
                        putExtra(Constants.BUNDLE_STORY_TITLE, story.title)
                        putExtra(Constants.BUNDLE_STORY_AUTHOR, story.by)
                        putExtra(Constants.BUNDLE_STORY_CREATED_AT, story.time)
                        putExtra(Constants.BUNDLE_STORY_TYPE, story.type)
                        putExtra(Constants.BUNDLE_STORY_SCORE, story.score.toString())
                        putExtra(Constants.BUNDLE_STORY_URL, story.url)
                    })
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
        fetchStoryIdList()
       binding.topStoryList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is androidx.recyclerview.widget.LinearLayoutManager) {
                        if (dy > 0) //check for scroll down
                        {
                            if (loading) {
                                if ((layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition()) >= layoutManager.itemCount) {
                                    loading = false
                                    firstIndex = lastIndex
                                    lastIndex += 10
                                    if (lastIndex > cachedStoryIdList.size-1) {
                                        lastIndex = cachedStoryIdList.size-1
                                    }
                                    if (lastIndex <= cachedStoryIdList.size-1) {
                                        fetchStoryById(cachedStoryIdList.subList(firstIndex, lastIndex), 0, cachedStoryIdList.subList(firstIndex, lastIndex).size)
                                    }
                                }
                            }
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            },)
        return binding.root
    }


    private fun setupUI() {
        binding.topStoryList.adapter = storyAdapter
    }

    private fun fetchStoryIdList() {
        topStoriesViewModel.getTopStoriesIDList(VALUE_TYPE_TOP_STORY).observe(viewLifecycleOwner, {
            it?.let { storyIdList ->
                when (storyIdList.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        storyIdList.data?.let { idList ->
                            cachedStoryIdList =  idList
                            cachedStoryList = ArrayList()
                            idList.subList(this.firstIndex, this.lastIndex).let { subList ->
                                fetchStoryById(subList, 0, subList.size)
                            }
                        }
                    }
                    Status.ERROR   -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(requireContext(), "Load", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchStoryById(storyIdList: List<Long>, firstIndex:Int, lastIndex: Int) {
        topStoriesViewModel.getTopStoriesById(storyIdList[firstIndex].toString())
            .observe(viewLifecycleOwner, { storyResource ->
                    storyResource?.let { story ->
                        when (story.status) {
                            Status.SUCCESS -> {
                                cachedStoryList.add(story.data !!)
                                if (firstIndex == lastIndex-1) {
                                    loading = true
                                    binding.progressBar.visibility = View.GONE
                                    storyAdapter.submitList(cachedStoryList)
                                    storyAdapter.notifyDataSetChanged()
                                }
                                if (firstIndex != lastIndex-1) {
                                    loading = false
                                    fetchStoryById(storyIdList, (firstIndex+1), lastIndex)
                                }
                            }
                            Status.ERROR   -> {
                                binding.progressBar.visibility = View.GONE
                            }
                            Status.LOADING -> {
                                binding.progressBar.visibility = View.VISIBLE
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
        private const val VALUE_TYPE_TOP_STORY = "topstories"
    }
}