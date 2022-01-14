package com.steven.newshacker.ui.search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.retrofit.coroutines.utils.Status
import com.steven.newshacker.adapter.StoryAdapter
import com.steven.newshacker.databinding.ActivitySearchBinding
import com.steven.newshacker.listener.OnStoryItemInteractionListener
import com.steven.newshacker.model.HitsModel
import com.steven.newshacker.model.StoryModel
import com.steven.newshacker.network.SearchApiHelper
import com.steven.newshacker.network.SearchNetworkApiClient
import com.steven.newshacker.network.StoryApiHelper
import com.steven.newshacker.network.StoryNetWorkApiClient
import com.steven.newshacker.ui.SearchViewModelFactory
import com.steven.newshacker.ui.article.ArticleActivity
import com.steven.newshacker.ui.comments.CommentsActivity
import com.steven.newshacker.utils.Constants

class SearchActivity : AppCompatActivity() {

    private var searchQuery = ""

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var binding: ActivitySearchBinding

    private var totalCount = 0

    private var cachedStoryIdList: List<HitsModel> = ArrayList()

    private var cachedStoryList = ArrayList<StoryModel>()

    private var loading = false

    private var firstIndex = 0

    private var lastIndex = 9

    private val storyAdapter by lazy {
        StoryAdapter(object : OnStoryItemInteractionListener {
            override fun onCommentClicked(story: StoryModel) {
                val commentBundle =
                        bundleOf(Constants.KEY_BUNDLE_OF__STORY_COMMENTS to story.kids)
                startActivity(Intent(this@SearchActivity, CommentsActivity::class.java).apply {
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
                startActivity(Intent(this@SearchActivity, ArticleActivity::class.java).apply {
                    putExtra(Constants.BUNDLE_STORY_TITLE, story.title)
                    putExtra(Constants.BUNDLE_STORY_AUTHOR, story.by)
                    putExtra(Constants.BUNDLE_STORY_CREATED_AT, story.time)
                    putExtra(Constants.BUNDLE_STORY_TYPE, story.type)
                    putExtra(Constants.BUNDLE_STORY_SCORE, story.score.toString())
                    putExtra(Constants.BUNDLE_STORY_URL, story.url)
                })
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.storyList.adapter = storyAdapter
        searchQuery = intent?.getStringExtra(Constants.KEY_BUNDLE_SEARCH_QUERY).orEmpty()
        setTitle(searchQuery)
        binding.storyList.addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        val layoutManager = recyclerView.layoutManager
                        if (layoutManager is androidx.recyclerview.widget.LinearLayoutManager) {
                            if (dy > 0) {
                                if (loading) {
                                    if ((layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition()) >= layoutManager.itemCount) {
                                        loading = false
                                        firstIndex = lastIndex
                                        lastIndex += 10
                                        // If the number of story is lesser then the initial count then assign totalCount-1 as last count
                                        if (lastIndex > totalCount) {
                                            lastIndex = totalCount-1
                                        }
                                        if (lastIndex <= cachedStoryIdList.size - 1) {
                                            fetchStoryById(cachedStoryIdList.subList(firstIndex,
                                                    lastIndex),
                                                    0,
                                                    cachedStoryIdList.subList(firstIndex, lastIndex).size)
                                        }
                                    }
                                }
                            }
                        }
                        super.onScrolled(recyclerView, dx, dy)
                    }
                },
        )
        searchViewModel = ViewModelProvider(this,
                SearchViewModelFactory(
                        SearchApiHelper(SearchNetworkApiClient.SEARCH_API_SERVICE),
                        StoryApiHelper(StoryNetWorkApiClient.STORY_API_SERVICE),
                ))[SearchViewModel::class.java]
        fetchStoryBySearchQuery(searchQuery)
    }

    private fun fetchStoryBySearchQuery(searchQuery: String) {
        searchViewModel.getStoryBySearchQuery(
                hitsPerPage = VALUE_HITS_PER_PAGE,
                tags = VALUE_TAG,
                attributesToRetrieve = VALUE_ATTRIBUTE_TO_RETRIEVE,
                attributesToHighlight = VALUE_ATTRIBUTE_TO_HIGHLIGHT,
                query = searchQuery,
        ).observe(this, {
            it?.let { storyIdList ->
                when (storyIdList.status) {
                    Status.SUCCESS -> {
                        storyIdList.data?.let {
                            totalCount = it.nbHits.toInt()
                            cachedStoryIdList = it.hits
                            cachedStoryList = ArrayList()
                            // If the number of story is lesser then the initial count then assign totalCount-1 as last count
                            if (lastIndex > totalCount) {
                                lastIndex = totalCount-1
                            }
                            fetchStoryById(storyIdList = storyIdList.data?.hits!!, firstIndex = 0, lastIndex = lastIndex)
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

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchStoryById(storyIdList: List<HitsModel>, firstIndex: Int, lastIndex: Int) {
        searchViewModel.getStoriesById(storyIdList[firstIndex].objectID)
                .observe(this, { storyResource ->
                    storyResource?.let { story ->
                        when (story.status) {
                            Status.SUCCESS -> {
                                cachedStoryList.add(story.data!!)
                                if (firstIndex == lastIndex - 1) {
                                    loading = true
                                    binding.progressBar.visibility = View.GONE
                                    storyAdapter.submitList(cachedStoryList)
                                    storyAdapter.notifyDataSetChanged()
                                }
                                if (firstIndex != lastIndex - 1) {
                                    loading = false
                                    fetchStoryById(storyIdList, (firstIndex + 1), lastIndex)
                                }
                            }
                            Status.ERROR   -> {
                                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                            }
                            Status.LOADING -> {
                                Toast.makeText(this, "LOADING", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    companion object {
        private const val VALUE_TAG = "story"
        private const val VALUE_HITS_PER_PAGE = "100"
        private const val VALUE_ATTRIBUTE_TO_RETRIEVE = "objectID"
        private const val VALUE_ATTRIBUTE_TO_HIGHLIGHT = "none"
    }
}