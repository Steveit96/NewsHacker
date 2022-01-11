package com.steven.newshacker.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.steven.newshacker.model.MoreOptionModel

class MoreViewModel : ViewModel() {

    private val _optionList = MutableLiveData<List<MoreOptionModel>>().apply {
        value = listOf(
            MoreOptionModel("Ask", 1),
            MoreOptionModel("Show", 2),
            MoreOptionModel("Job Stories", 3),
        )
    }

    val optionList: LiveData<List<MoreOptionModel>> = _optionList
}