package com.steven.newshacker.ui.topstories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.steven.newshacker.adapter.StoryAdapter
import com.steven.newshacker.databinding.FragmentTopStoriesBinding
import com.steven.newshacker.listener.OnStoryItemInteractionListener
import com.steven.newshacker.model.StoryModel

class TopStoriesFragment : Fragment() {

    private lateinit var topStoriesViewModel: TopStoriesViewModel
    private var _binding: FragmentTopStoriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val storyAdapter by lazy {
        StoryAdapter(
            object: OnStoryItemInteractionListener {
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
        topStoriesViewModel = ViewModelProvider(this)[TopStoriesViewModel::class.java]
        _binding = FragmentTopStoriesBinding.inflate(inflater, container, false)
        binding.topStoryList.adapter = storyAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<StoryModel>()
        list.add(
            StoryModel(
                by = "dhouston",
                descendants = 71,
                id = 8863,
                kids = listOf(8952, 9224, 8917, 8884, 8887, 8943, 8869, 8958, 9005, 9671, 8940, 9067, 8908, 9055, 8865, 8881, 8872, 8873, 8955, 10403, 8903, 8928, 9125, 8998, 8901,),
                score = 111,
                time = 1175714200,
                title = "My YC app: Dropbox - Throw away your USB drive",
                type = "story",
                url = "http://www.getdropbox.com/u/2/screencast.html"
        ))
        list.add(
            StoryModel(
                by = "dhouston",
                descendants = 72,
                id = 8863,
                kids = listOf(8952, 9224, 8917, 8884, 8887, 8943, 8869, 8958, 9005, 9671, 8940, 9067, 8908, 9055, 8865, 8881, 8872, 8873, 8955, 10403, 8903, 8928, 9125, 8998, 8901,),
                score = 111,
                time = 1175714200,
                title = "My YC app: Dropbox - Throw away your USB drive",
                type = "story",
                url = "http://www.getdropbox.com/u/2/screencast.html"
            ))
        list.add(
            StoryModel(
                by = "dhouston",
                descendants = 73,
                id = 8863,
                kids = listOf(8952, 9224, 8917, 8884, 8887, 8943, 8869, 8958, 9005, 9671, 8940, 9067, 8908, 9055, 8865, 8881, 8872, 8873, 8955, 10403, 8903, 8928, 9125, 8998, 8901,),
                score = 111,
                time = 1175714200,
                title = "My YC app: Dropbox - Throw away your USB drive",
                type = "story",
                url = "http://www.getdropbox.com/u/2/screencast.html"
            ))
        list.add(
            StoryModel(
                by = "dhouston",
                descendants = 74,
                id = 8863,
                kids = listOf(8952, 9224, 8917, 8884, 8887, 8943, 8869, 8958, 9005, 9671, 8940, 9067, 8908, 9055, 8865, 8881, 8872, 8873, 8955, 10403, 8903, 8928, 9125, 8998, 8901,),
                score = 111,
                time = 1175714200,
                title = "My YC app: Dropbox - Throw away your USB drive",
                type = "story",
                url = "http://www.getdropbox.com/u/2/screencast.html"
            ))
        list.add(
            StoryModel(
                by = "dhouston",
                descendants = 75,
                id = 8863,
                kids = listOf(8952, 9224, 8917, 8884, 8887, 8943, 8869, 8958, 9005, 9671, 8940, 9067, 8908, 9055, 8865, 8881, 8872, 8873, 8955, 10403, 8903, 8928, 9125, 8998, 8901,),
                score = 111,
                time = 1175714200,
                title = "My YC app: Dropbox - Throw away your USB drive",
                type = "story",
                url = "http://www.getdropbox.com/u/2/screencast.html"
            ))
        storyAdapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}