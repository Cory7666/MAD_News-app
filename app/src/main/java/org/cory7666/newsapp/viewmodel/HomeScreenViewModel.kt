package org.cory7666.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.news.NewsApiNewsProvider
import org.cory7666.newsapp.data.news.NewsInfo
import java.util.*
import java.util.stream.Collectors

class HomeScreenViewModel : ViewModel()
{
  private val _toastMessageText = MutableLiveData<Int>(0)
  private val _newsList = MutableLiveData<List<NewsInfo>>(LinkedList())
  val newsList: LiveData<List<NewsInfo>> = _newsList
  val toastMessageText: LiveData<Int> = _toastMessageText

  fun update()
  {
    NewsApiNewsProvider("e131c06345f444a1a953bff9c1f954e1",
      NewsApiNewsProvider.Language.RU,
      object : NewsApiClient.ArticlesResponseCallback
      {
        override fun onSuccess(response: ArticleResponse?)
        {
          val newArticles =
            response?.articles
              ?.parallelStream()
              ?.map { x -> NewsInfo(x.title, x.description, x.url) }
              ?.collect(Collectors.toList()) ?: emptyList()
          _newsList.value = newArticles
        }

        override fun onFailure(throwable: Throwable?)
        {
          throwable?.printStackTrace()
          _toastMessageText.value = R.string.text_network_error
        }
      }).getNews()
  }
}