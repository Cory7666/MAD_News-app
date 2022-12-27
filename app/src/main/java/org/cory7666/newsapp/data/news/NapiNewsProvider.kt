package org.cory7666.newsapp.data.news

import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.NewsApiClient.ArticlesResponseCallback
import com.kwabenaberko.newsapilib.models.request.EverythingRequest

/**
 * News provider from the site newsapi.org by using their JavaAPI.
 */
class NapiNewsProvider(
  private val apiKey: String, // apiKey
  private val language: Language, // language
  private val callback: ArticlesResponseCallback // what to do
) : NewsProvider<NewsInfo>
{
  enum class Language(val code: String)
  {
    RU("ru"),
    EN("en");
  }

  override fun getSomeNews(page: Int, count: Int): List<NewsInfo>
  {
    // Construct NewsAPI-lib object and call 'getEverything' method to get
    // all kinds of news
    NewsApiClient(apiKey).getEverything(
        EverythingRequest
          .Builder()
          .language(language.code)
          .q("all")
          .page(page)
          .pageSize(count)
          .build(), callback
      )

    // return empty list because the requested news will be processed
    // in the callback
    return emptyList()
  }
}