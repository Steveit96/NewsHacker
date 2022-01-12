package com.steven.newshacker.ui.article

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import com.steven.newshacker.databinding.ActivityArticleBinding
import com.steven.newshacker.utils.Constants
import java.lang.Exception
import java.util.*
import android.webkit.WebView
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs


class ArticleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ArticleActivity"
    }

    private lateinit var binding: ActivityArticleBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpUI() {
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
            binding.layoutWeb.webViewClient = WebViewClient()
            binding.layoutWeb.loadUrl( intent.getStringExtra(Constants.BUNDLE_STORY_URL).toString())
            binding.layoutWeb.settings.javaScriptEnabled = true
            binding.layoutWeb.settings.setSupportZoom(true)

            binding.layoutWeb.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    binding.nestedScrollView.visibility = View.VISIBLE
                    binding.appBarLayout.visibility = View.VISIBLE
                    binding.layoutWeb.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message.orEmpty())
        }
    }

    private fun getDate(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time * 1000
        return android.text.format.DateFormat.format("dd-MM-yyyy", cal).toString()
    }
}