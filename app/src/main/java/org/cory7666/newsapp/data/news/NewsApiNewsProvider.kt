package org.cory7666.newsapp.data.news

import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.NewsApiClient.ArticlesResponseCallback
import com.kwabenaberko.newsapilib.models.request.EverythingRequest

class NewsApiNewsProvider(
  private val apiKey: String,
  private val language: Language,
  private val callback: ArticlesResponseCallback
) : NewsProvider<NewsInfo>
{
  enum class Language(val code: String)
  {
    RU("ru"),
    EN("en");
  }

  override fun getFew(page: Int, count: Int): List<NewsInfo>
  {
    NewsApiClient(apiKey).getEverything(
      EverythingRequest.Builder().language(
        language.code
      ).q("all").page(page).pageSize(count).build(), callback
    )
    return emptyList()
  }
}