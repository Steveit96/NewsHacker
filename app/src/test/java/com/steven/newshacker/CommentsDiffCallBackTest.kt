package com.steven.newshacker

import com.steven.newshacker.adapter.diffcallback.CommentsDiffCallBack
import com.steven.newshacker.model.CommentModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CommentsDiffCallBackTest {

    private val diffCallBack = CommentsDiffCallBack()

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
        private val itemOne = CommentModel(
                by =  "norvig",
                id = 2921983,
                kids = listOf(2922097, 2922429, 2924562, 2922709, 2922573, 2922140, 2922141),
                parent= 2921506,
                text = "Aw shucks, guys ... you make me blush with your compliments.<p>Tell you what, Ill make a deal: I'll keep writing if you keep reading. K?",
                time = 1314211127,
                type = "comment"
        )

        private val itemTwo = CommentModel(
                by =  "stephan",
                id = 2921984,
                kids = listOf(2922097, 2922429, 2924562, 2922709, 2922573, 2922140, 2922141),
                parent= 2921507,
                text = "test test",
                time = 1314211127,
                type = "comment"
        )
    }
}