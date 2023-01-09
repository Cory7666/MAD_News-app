/*
 *   Copyright 2022 Alex Filozop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cory7666.newsapp.data.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.NewsApiClient.ArticlesResponseCallback
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import java.util.stream.Collectors

/**
 * News provider from the site newsapi.org by using their JavaAPI.
 */
class NapiNewsProvider(
  private val apiKey: String, // apiKey
  private val language: Language // language
) : NewsProvider<NewsInfo>
{

  private val _newsLiveData = MutableLiveData<List<NewsInfo>>()
  private val _error = MutableLiveData<Throwable>()

  val news: LiveData<List<NewsInfo>> = _newsLiveData
  val error: LiveData<Throwable> = _error

  override fun getSomeNewsAsync(page: Int, count: Int)
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
  }

  private val callback = object : ArticlesResponseCallback
  {
    override fun onSuccess(response: ArticleResponse?)
    {
      _newsLiveData.value =
        response?.articles
          ?.stream()
          ?.map { x -> NewsInfo(x.title, x.description, x.url) }
          ?.collect(Collectors.toList())
    }

    override fun onFailure(throwable: Throwable?)
    {
      if (throwable != null)
      {
        _error.value = throwable as Throwable
      }
    }
  }

  enum class Language(val code: String)
  {
    RU("ru"),
    EN("en");
  }
}