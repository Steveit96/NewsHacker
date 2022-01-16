package com.steven.newshacker.ui.newstories

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.retrofit.coroutines.utils.Status
import com.steven.newshacker.R
import com.steven.newshacker.adapter.StoryAdapter
import com.steven.newshacker.databinding.FragmentNewStoriesBinding
import com.steven.newshacker.isNetWorkAvailable
import com.steven.newshacker.listener.OnStoryItemInteractionListener
import com.steven.newshacker.model.StoryModel
import com.steven.newshacker.networkNotAvailableToast
import com.steven.newshacker.ui.article.ArticleActivity
import com.steven.newshacker.ui.comments.CommentsActivity
import com.steven.newshacker.ui.search.SearchActivity
import com.steven.newshacker.viewmodel.StoriesViewModel
import com.steven.newshacker.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewStoriesFragment : Fragment() {

    private val storiesViewModel by viewModels<StoriesViewModel>()

    private var _binding: FragmentNewStoriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var  firstIndex = 0

    private var lastIndex = 10

    private var cachedStoryIdList: List<Long> = ArrayList()

    private var cachedStoryList= ArrayList<StoryModel>()

    private var loading = false

    private val storyAdapter by lazy {
        StoryAdapter(
            object : OnStoryItemInteractionListener {
                override fun onCommentClicked(story: StoryModel) {
                    if (requireContext().isNetWorkAvailable()) {
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
                    } else {
                        requireContext().networkNotAvailableToast()
                    }
                }

                override fun onArticleClicked(story: StoryModel) {
                    if (requireContext().isNetWorkAvailable()) {
                        startActivity(Intent(activity, ArticleActivity::class.java).apply {
                            putExtra(Constants.BUNDLE_STORY_TITLE, story.title)
                            putExtra(Constants.BUNDLE_STORY_AUTHOR, story.by)
                            putExtra(Constants.BUNDLE_STORY_CREATED_AT, story.time)
                            putExtra(Constants.BUNDLE_STORY_TYPE, story.type)
                            putExtra(Constants.BUNDLE_STORY_SCORE, story.score.toString())
                            putExtra(Constants.BUNDLE_STORY_URL, story.url)
                        })
                    } else {
                        requireContext().networkNotAvailableToast()
                    }
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search: SearchView = menu.findItem(R.id.search).actionView as SearchView
        search.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty().not()) {
                    startActivity(Intent(
                            requireActivity(),
                            SearchActivity::class.java
                    ).putExtra(Constants.KEY_BUNDLE_SEARCH_QUERY, query))
                } else {
                    Toast.makeText(requireContext(), "Blank Query not allowed !", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentNewStoriesBinding.inflate(inflater, container, false)
        binding.swipeToRefresh.setOnRefreshListener {
            setupUI(true)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeToRefresh.isRefreshing = false
            }, Constants.SWIPE_DEFAULT_LOAD_TIME)
        }
        setupUI(false)
        return binding.root
    }

    private fun setupUI(isRefresh: Boolean) {
        binding.newStoryList.adapter = storyAdapter
        if (isRefresh) {
            storyAdapter.submitList(emptyList())
        }
        binding.newStoryList.addOnScrollListener(
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
        if (requireContext().isNetWorkAvailable()) {
            fetchStoryIdList()
        } else {
            fetchFromDB()
        }
    }

    private fun fetchStoryIdList() {
        storiesViewModel.getTopStoriesIDList(VALUE_TYPE_NEW_STORY).observe(viewLifecycleOwner, {
            it?.let { storyIdList ->
                when (storyIdList.status) {
                      Status.SUCCESS -> {
                        storyIdList.data?.let { idList ->
                            cachedStoryIdList =  idList
                            cachedStoryList = ArrayList()
                            Log.d(TAG, "$VALUE_TYPE_NEW_STORY List fetch : $idList")
                            idList.subList(this.firstIndex, this.lastIndex).let { subList ->
                                fetchStoryById(subList, 0, subList.size)
                            }
                        }
                    }

                   Status.LOADING -> {
                    }

                   Status.ERROR -> {
                       Log.d(TAG, "$VALUE_TYPE_NEW_STORY List fetch : "+storyIdList.message)
                    }
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchStoryById(storyIdList: List<Long>, firstIndex: Int, lastIndex: Int) {
        storiesViewModel.getTopStoriesById(storyIdList[firstIndex].toString(), VALUE_TYPE_NEW_STORY)
                .observe(viewLifecycleOwner, { storyResource ->
                    storyResource?.let { story ->
                        when (story.status) {
                            Status.SUCCESS -> {
                                story.data!!.apply {
                                    storyType = VALUE_TYPE_NEW_STORY
                                }
                                cachedStoryList.add(story.data)
                                Log.d(TAG, "$VALUE_TYPE_NEW_STORY fetch : "+story.data.toString())
                                if (firstIndex == lastIndex - 1) {
                                    loading = true
                                    binding.progressBar.visibility = View.GONE
                                    storyAdapter.submitList(cachedStoryList)
                                    storyAdapter.notifyDataSetChanged()
                                    insertStories(cachedStoryList)
                                }
                                if (firstIndex != lastIndex - 1) {
                                    loading = false
                                    fetchStoryById(storyIdList, (firstIndex + 1), lastIndex)
                                }
                            }
                            Status.ERROR   -> {
                                binding.progressBar.visibility = View.GONE
                                Log.d(TAG, "$VALUE_TYPE_NEW_STORY fetch : "+story.message)
                            }
                            Status.LOADING -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                        }
                    }
                })
    }

    private fun fetchFromDB() {
        storiesViewModel.fetchAllStoriesFromDB(VALUE_TYPE_NEW_STORY).observe( viewLifecycleOwner, {
            storyResource ->
            storyResource?.let { story ->
                when (story.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        storyAdapter.submitList(story.data)
                        Log.d(TAG, "Fetch $VALUE_TYPE_NEW_STORY from DB:" + story.data.toString())
                    }
                    Status.ERROR   -> {
                        binding.progressBar.visibility = View.GONE
                        Log.d(TAG, "Fetch $VALUE_TYPE_NEW_STORY from DB:" + story.message)
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun insertStories(storyList: List<StoryModel>) {
        storiesViewModel.insertAllStories(storyList).observe( viewLifecycleOwner, {
            insertAction ->
            insertAction?.let { insert ->
                when (insert.status) {
                    Status.SUCCESS -> {
                        Log.d(TAG, "${VALUE_TYPE_NEW_STORY}: ${insert.data.orEmpty()}")
                    }
                    Status.LOADING -> {
                        Log.d(TAG, "${VALUE_TYPE_NEW_STORY}: Insert Loading}")
                    }
                    Status.ERROR   -> {
                        Log.d(TAG, "${VALUE_TYPE_NEW_STORY}: ${insert.message.orEmpty()}")
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
        private const val VALUE_TYPE_NEW_STORY = "newstories"
        private const val TAG = "NewStoriesFragment"
    }
}