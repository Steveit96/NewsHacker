package com.steven.newshacker.ui.comments

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import androidx.activity.viewModels

import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.mindorks.retrofit.coroutines.utils.Status
import com.steven.newshacker.adapter.CommentsAdapter
import com.steven.newshacker.databinding.ActivityCommentsBinding
import com.steven.newshacker.isNetWorkAvailable
import com.steven.newshacker.listener.OnCommentsInteractionListener
import com.steven.newshacker.model.CommentModel
import com.steven.newshacker.networkNotAvailableToast
import com.steven.newshacker.utils.Constants
import com.steven.newshacker.viewmodel.CommentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

@AndroidEntryPoint
class CommentsActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "CommentsActivity"
    }

    private lateinit var binding: ActivityCommentsBinding

    private val commentsViewModel by viewModels<CommentsViewModel>()

    private var  firstIndex = 0

    private var lastIndex = 10

    private var cachedCommentIdList: List<Long> = ArrayList()

    private var cachedStoryList= ArrayList<CommentModel>()

    private var loading = false

    private val commentsAdapter by lazy {
        CommentsAdapter(
            object : OnCommentsInteractionListener {
                override fun commentsClicked(comment: CommentModel) {
                    if (this@CommentsActivity.isNetWorkAvailable()) {
                        val commentBundle = bundleOf(
                                Constants.KEY_BUNDLE_OF__STORY_COMMENTS to comment.kids)
                        startActivity(Intent(this@CommentsActivity, CommentsActivity::class.java).apply {
                            putExtra(Constants.BUNDLE_STORY_TITLE, comment.text)
                            putExtra(Constants.BUNDLE_STORY_AUTHOR, comment.by)
                            putExtra(Constants.BUNDLE_STORY_CREATED_AT, comment.time)
                            putExtra(Constants.BUNDLE_STORY_TYPE, comment.type)
                            putExtra(Constants.BUNDLE_STORY_SCORE, "")
                            putExtra(Constants.BUNDLE_STORY_URL, "")
                            putExtra(Constants.BUNDLE_STORY_COMMENTS, commentBundle)
                        })
                        finish()
                    } else {
                       this@CommentsActivity.networkNotAvailableToast()
                    }
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
    }

    private fun   setUpUI() {
        binding.listComments.adapter = commentsAdapter
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                binding.layoutCollapse.title = "Comments"
            } else {
                binding.layoutCollapse.title = ""
            }
        })
        try {
            intent.extras?.get(Constants.BUNDLE_STORY_TITLE)?.toString()?.let {
                if (it.isNotEmpty()) {
                    binding.layoutHeader.txtHeaderTitle.visibility = View.VISIBLE
                    binding.layoutHeader.txtHeaderTitle.text = Html.fromHtml(it)
                } else {
                    binding.layoutHeader.txtHeaderTitle.visibility = View.GONE
                }
            }
            intent.extras?.get(Constants.BUNDLE_STORY_AUTHOR)?.toString()?.let {
                if (it.isNotEmpty()) {
                    binding.layoutHeader.labelStoryAuthor.visibility = View.VISIBLE
                    binding.layoutHeader.labelStoryAuthor.text = it
                } else {
                    binding.layoutHeader.labelStoryAuthor.visibility = View.GONE
                }
            }
            intent.extras?.get(Constants.BUNDLE_STORY_CREATED_AT)?.toString()?.let {
                if (it.isNotEmpty()) {
                    binding.layoutHeader.labelStoryCreatedAt.visibility = View.VISIBLE
                    binding.layoutHeader.labelStoryCreatedAt.text = getDate(it.toLong())
                } else {
                    binding.layoutHeader.labelStoryCreatedAt.visibility = View.GONE
                }
            }
            intent.extras?.get(Constants.BUNDLE_STORY_TYPE)?.toString()?.let {
                if (it.isNotEmpty()) {
                    binding.layoutHeader.labelType.visibility = View.VISIBLE
                    binding.layoutHeader.labelType.text = it
                } else {
                    binding.layoutHeader.labelType.visibility = View.GONE
                }
            }
            intent.extras?.get(Constants.BUNDLE_STORY_SCORE)?.toString()?.let {
                if (it.isNotEmpty()) {
                    binding.layoutHeader.labelScore.visibility = View.VISIBLE
                    binding.layoutHeader.labelScore.text = it
                } else {
                    binding.layoutHeader.labelScore.visibility = View.GONE
                }
            }
            binding.nestedScrollView.visibility = View.VISIBLE
            binding.appBarLayout.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE

            intent.getBundleExtra(Constants.BUNDLE_STORY_COMMENTS)?.let {
                cachedCommentIdList =  it.get(Constants.KEY_BUNDLE_OF__STORY_COMMENTS) as ArrayList<Long>
                if (lastIndex <= cachedCommentIdList.size-1) {
                    fetchComments(cachedCommentIdList, firstIndex, lastIndex)
                } else {
                    fetchComments(cachedCommentIdList, firstIndex, cachedCommentIdList.size)
                }
            }

            binding.listComments.addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        val layoutManager = recyclerView.layoutManager
                        if (layoutManager is androidx.recyclerview.widget.LinearLayoutManager) {
                            if (dy > 0) //check for scroll down
                            {
                                if (loading && cachedCommentIdList.isNullOrEmpty()) {
                                    if ((layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition()) >= layoutManager.itemCount) {
                                        loading = false
                                        firstIndex = lastIndex
                                        lastIndex += 10
                                        if (lastIndex > cachedCommentIdList.size-1) {
                                            lastIndex = cachedCommentIdList.size-1
                                        }
                                        if (lastIndex <= cachedCommentIdList.size-1) {
                                            fetchComments(
                                                cachedCommentIdList.subList(firstIndex, lastIndex),
                                                0,
                                                cachedCommentIdList.subList(firstIndex, lastIndex).size)
                                        }
                                    }
                                }
                            }
                        }
                        super.onScrolled(recyclerView, dx, dy)
                    }
                },)

        } catch (e: Exception) {
            Log.e(TAG, e.message.orEmpty())
        }
    }

    private fun getDate(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time * 1000
        return DateFormat.format("dd-MM-yyyy", cal).toString()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchComments(storyIdList: List<Long>, firstIndex:Int, lastIndex: Int) {
        commentsViewModel.getComments(storyIdList[firstIndex].toString())
                .observe(this, { storyResource ->
                    storyResource?.let { story ->
                        when (story.status) {
                            Status.SUCCESS -> {
                                cachedStoryList.add(story.data !!)
                                if (firstIndex == lastIndex-1) {
                                    loading = true
                                    binding.progressBar.visibility = View.GONE
                                    commentsAdapter.submitList(cachedStoryList)
                                    commentsAdapter.notifyDataSetChanged()
                                }
                                if (firstIndex != lastIndex-1) {
                                    loading = false
                                    fetchComments(storyIdList, (firstIndex+1), lastIndex)
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
}