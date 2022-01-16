package com.steven.newshacker

import com.steven.newshacker.adapter.diffcallback.StoryDiffCallBack
import com.steven.newshacker.model.StoryModel
import org.junit.Assert.assertEquals
import org.junit.Test

class StoryDiffCallBackTest {

    private val diffCallBack = StoryDiffCallBack()

    @Test
    fun areItemsTheSameTest() {
        assertEquals(diffCallBack.areItemsTheSame(itemOne, itemOne), true)
        assertEquals(diffCallBack.areItemsTheSame(itemOne, itemTwo), false)
    }

    @Test
    fun areContentsTheSameTest() {
        assertEquals(diffCallBack.areContentsTheSame(itemOne, itemOne), true)
        assertEquals(diffCallBack.areContentsTheSame(itemOne, itemTwo), false)
    }

    companion object {
        private val itemOne = StoryModel(
                by =  "norvig",
                id = 2921983,
                title = "Aw shucks, guys ... you make me blush with your compliments.<p>Tell you what, Ill make a deal: I'll keep writing if you keep reading. K?",
                time = 1314211127,
                type = "comment"
        )

        private val itemTwo = StoryModel(
                by =  "stephan",
                id = 2921984,
                title = "test test",
                time = 1314211127,
                type = "comment"
        )
    }
}