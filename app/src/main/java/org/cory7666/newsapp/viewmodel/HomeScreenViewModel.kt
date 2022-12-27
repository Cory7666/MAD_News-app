package org.cory7666.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.news.NapiNewsProvider
import org.cory7666.newsapp.data.news.NewsInfo
import org.cory7666.newsapp.data.story.FirestoreStoryProvider
import org.cory7666.newsapp.data.story.StoryInfo
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.streams.toList

class HomeScreenViewModel : ViewModel()
{
  private val newsRepository = NapiNewsProvider(
    "e131c06345f444a1a953bff9c1f954e1", NapiNewsProvider.Language.RU
  )
  private val storyProvider = FirestoreStoryProvider()

  private val _toastMessageText = MutableLiveData<Int>(0)
  private val _storiesList = MutableLiveData<List<StoryInfo>>(LinkedList())
  private val _isRefreshing = MutableLiveData<Boolean>(false)
  private val _isReversedRefreshing = MutableLiveData<Boolean>(false)

  val toastMessageText: LiveData<Int> = _toastMessageText
  val storiesList: LiveData<List<StoryInfo>> = _storiesList
  val newsList: LiveData<List<NewsInfo>> = _newsList
  val isRefreshing: LiveData<Boolean> = _isRefreshing
  val isReversedRefreshing: LiveData<Boolean> = _isReversedRefreshing

  init
  {

    newsRepository.news.observeForever {
      _newsList.value =
        Stream
          .concat(_newsList.value?.stream(), it?.stream())
          .collect(Collectors.toList()) ?: emptyList()
      ++pageNumber

      _isRefreshing.value = false
      _isReversedRefreshing.value = false
    }

    newsRepository.error.observeForever {
      it?.printStackTrace()

      if (it?.message!!.startsWith("You have requested too many results."))
      {
        _toastMessageText.value = R.string.text_error_api_restriction
      }
      else
      {
        _toastMessageText.value = R.string.text_network_error
      }

      _isRefreshing.value = false
      _isReversedRefreshing.value = false
    }

    storyProvider.stories.observeForever {
      _storiesList.value = it
    }

    storyProvider.error.observeForever {
      _toastMessageText.value = R.string.text_network_error
    }
  }

  fun clearAndGetNews()
  {
    _newsList.value =
      _newsList.value?.stream()?.limit(perPageArticlesCount + 0L)?.toList()
        ?: emptyList()
    if (_newsList.value.isNullOrEmpty())
    {
      pageNumber = 1
      _isRefreshing.value = true
      updateNewsList()
    }
    else
    {
      pageNumber = 2
    }
  }

  fun updateNewsList()
  {
    Log.d(
      "TAG",
      "updateNewsList: load articles page=${pageNumber}, count=${perPageArticlesCount}."
    )

    if (!(_isRefreshing.value as Boolean))
    {
      _isReversedRefreshing.value = true
    }

    newsRepository.getSomeNewsAsync(
      page = pageNumber, count = perPageArticlesCount
    )
  }

  fun updateStoriesList()
  {
    storyProvider.getAsync()
  }

  companion object
  {
    private val perPageArticlesCount: Int = 10
    private var pageNumber: Int = 1
    private val _newsList = MutableLiveData<List<NewsInfo>>(LinkedList())
  }
}
