package org.cory7666.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.news.NewsApiNewsProvider
import org.cory7666.newsapp.data.news.NewsInfo
import org.cory7666.newsapp.data.story.FirestoreStoryProvider
import org.cory7666.newsapp.data.story.StoryInfo
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class HomeScreenViewModel : ViewModel()
{
  private val perPageArticlesCount: Int = 10
  private var pageNumber: Int = 1

  private val _toastMessageText = MutableLiveData<Int>(0)
  private val _storiesList = MutableLiveData<List<StoryInfo>>(LinkedList())
  private val _newsList = MutableLiveData<List<NewsInfo>>(LinkedList())
  private val _isRefreshing = MutableLiveData<Boolean>(false)
  val toastMessageText: LiveData<Int> = _toastMessageText
  val storiesList: LiveData<List<StoryInfo>> = _storiesList
  val newsList: LiveData<List<NewsInfo>> = _newsList
  val isRefreshing: LiveData<Boolean> = _isRefreshing

  fun updateNewsList()
  {
    if (!(_isRefreshing.value as Boolean))
    {
      Log.d(
        "TAG",
        "updateNewsList: load articles page=${pageNumber}, count=${perPageArticlesCount}."
      )
      _isRefreshing.value = true

      NewsApiNewsProvider("e131c06345f444a1a953bff9c1f954e1",
        NewsApiNewsProvider.Language.RU,
        object : NewsApiClient.ArticlesResponseCallback
        {
          override fun onSuccess(response: ArticleResponse?)
          {
            val newArticlesList =
              response?.articles
                ?.parallelStream()
                ?.map { x -> NewsInfo(x.title, x.description, x.url) }
                ?.collect(Collectors.toList())

            val concatStream = Stream.concat(
              _newsList.value?.stream(), newArticlesList?.stream()
            )

            if (!newArticlesList.isNullOrEmpty())
            {
              ++pageNumber
            }

            _newsList.value =
              concatStream?.collect(Collectors.toList()) ?: emptyList()
            _isRefreshing.value = false
          }

          override fun onFailure(throwable: Throwable?)
          {
            throwable?.printStackTrace()
            _toastMessageText.value = R.string.text_network_error
            _isRefreshing.value = false
          }
        }).getFew(page = pageNumber, count = perPageArticlesCount)
    }
  }

  fun updateStoriesList()
  {
    FirestoreStoryProvider(_storiesList).get()
  }
}